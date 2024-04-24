package org.vedant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vedant.model.GeocodingResponse;

@Service
public class GeocodingService {
    private final RestTemplate restTemplate;

    private final String geocodingApiUrl;
    private final String apiKey;

    public GeocodingService(
                            @Value("${openweathermap.api.key}") String apiKey,
                            @Value("${openweathermap.api.cordUrl}") String geocodingApiUrl) {
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
        this.geocodingApiUrl = geocodingApiUrl;
    }

    public GeocodingResponse getCoordinates(String zipCode) {
        // Construct the API URL
        String apiUrl = String.format("%s?zip=%s,IN&appid=%s", geocodingApiUrl, zipCode, apiKey);

        // Make the API call and parse the response
        return restTemplate.getForObject(apiUrl, GeocodingResponse.class);
    }
}
