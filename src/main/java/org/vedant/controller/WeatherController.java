package org.vedant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vedant.service.WeatherService;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/{pincode}")
    public ResponseEntity<?> getWeatherInfoByPincodeAndDate(
            @PathVariable("pincode") String pincode,
            @RequestParam(name = "for_date", defaultValue = "", required = false) String forDate) {

        // Call service method to fetch weather information
        Object weatherInfo = weatherService.getWeatherInfo(pincode, forDate);

        // Check if weather information is found
        if (weatherInfo != null) {
            return ResponseEntity.ok(weatherInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weather information not found");
        }
    }
}
