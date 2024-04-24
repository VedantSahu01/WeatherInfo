package org.vedant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vedant.model.WeatherInfo;
import org.vedant.model.WeatherInfoResponse;
import org.vedant.util.DateUtil;

@Service
public class OpenWeatherMapService {

    private final RestTemplate restTemplate;
    private final DateUtil dateUtil;
    private final String apiKey;

    private static final String HISTORY_API_URL = "https://history.openweathermap.org/data/2.5/history/city";
    private static final String CURRENT_API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Autowired
    public OpenWeatherMapService(DateUtil dateUtil,
                                 @Value("${openweathermap.api.key}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.dateUtil = dateUtil;
        this.apiKey = apiKey;
    }

    public WeatherInfo getWeather(double lat, double lon, long start) {
        // Construct the API URL
        String apiUrl;
        WeatherInfo response = new WeatherInfo();
        if (start == dateUtil.getEpochFromDate("")) {
            apiUrl = String.format("%s?lat=%s&lon=%s&appid=%s",
                    CURRENT_API_URL, lat, lon, apiKey);
            // Make the API call and parse the response
            response = restTemplate.getForObject(apiUrl, WeatherInfo.class);
        } else {
            apiUrl = String.format("%s?lat=%s&lon=%s&type=day&start=%d&end=%d&appid=%s",
                    HISTORY_API_URL, lat, lon, start, start + 86400, apiKey);
            // Make the API call and parse the response
            WeatherInfoResponse responseList = restTemplate.getForObject(apiUrl, WeatherInfoResponse.class);
            if (responseList != null && !responseList.getList().isEmpty()) {
                response = responseList.getList().get(0);
            }
        }
        return response;
    }
}
