package org.vedant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vedant.model.GeocodingResponse;
import org.vedant.model.WeatherInfo;
import org.vedant.model.WeatherLocalModel;
import org.vedant.repository.WeatherLocalRepository;
import org.vedant.util.DateUtil;

@Service
public class WeatherService {

    private final WeatherLocalRepository weatherRepository;
    private final GeocodingService geocodingService;
    private final OpenWeatherMapService openWeatherMapService;
    private final DateUtil dateUtil;

    @Autowired
    public WeatherService(WeatherLocalRepository weatherRepository, GeocodingService geocodingService,
                          OpenWeatherMapService openWeatherMapService, DateUtil dateUtil) {
        this.weatherRepository = weatherRepository;
        this.geocodingService = geocodingService;
        this.openWeatherMapService = openWeatherMapService;
        this.dateUtil = dateUtil;
    }

    public WeatherInfo getWeatherInfo(String pincode, String date) {
        long epochDate = dateUtil.getEpochFromDate(date);

        // Check if weather information for the pincode and date is present in the database
        WeatherLocalModel weatherInfo = weatherRepository.findByPincodeAndDate(pincode, epochDate);

        if (weatherInfo == null || !weatherInfo.isLocal()) {
            // Weather information not found locally, call geocoding API
            GeocodingResponse geocodingResponse = geocodingService.getCoordinates(pincode);

            if (geocodingResponse != null) {
                // Geocoding successful, extract latitude and longitude
                double latitude = geocodingResponse.getLat();
                double longitude = geocodingResponse.getLon();

                // Call OpenWeatherMap API to get weather information
                WeatherInfo weatherResponse = openWeatherMapService.getWeather(latitude, longitude, epochDate);

                if (weatherResponse != null) {
                    // Mapping successful, save weather information to the database
                    weatherInfo = saveLocalInfo(pincode, epochDate, weatherResponse);
                } else {
                    // Failed to get weather information from OpenWeatherMap API
                    throw new RuntimeException("Failed to get weather information from OpenWeatherMap API");
                }
            } else {
                // Failed to get coordinates from geocoding API
                throw new RuntimeException("Failed to get coordinates from geocoding API");
            }
        }

        return weatherInfo.getWeatherInfo();
    }

    private WeatherLocalModel saveLocalInfo(String pincode, long epochDate, WeatherInfo weatherResponse) {
        WeatherLocalModel weatherInfo = new WeatherLocalModel();
        weatherInfo.setPincode(pincode);
        weatherInfo.setDate(epochDate);
        weatherInfo.setWeatherInfo(weatherResponse);
        weatherInfo.setLocal(true);
        return weatherRepository.save(weatherInfo);
    }
}
