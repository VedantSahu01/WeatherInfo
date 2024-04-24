package controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.vedant.controller.WeatherController;
import org.vedant.model.WeatherInfo;
import org.vedant.service.WeatherService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(SpringExtension.class)
public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    public void testGetWeatherInfoByPincodeAndDate_WeatherInfoFound() {
        // Mocking
        String pincode = "12345";
        String date = "2024-04-24";
        WeatherInfo mockWeatherInfo = new WeatherInfo();
        when(weatherService.getWeatherInfo(pincode, date)).thenReturn(new WeatherInfo());

        // Testing
        ResponseEntity<?> response = weatherController.getWeatherInfoByPincodeAndDate(pincode, date);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockWeatherInfo, response.getBody());
    }

    @Test
    public void testGetWeatherInfoByPincodeAndDate_WeatherInfoNotFound() {
        // Mocking
        String pincode = "12345";
        String date = "2024-04-24";
        when(weatherService.getWeatherInfo(pincode, date)).thenReturn(null);

        // Testing
        ResponseEntity<?> response = weatherController.getWeatherInfoByPincodeAndDate(pincode, date);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Weather information not found", response.getBody());
    }
}

