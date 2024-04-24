package service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.vedant.model.GeocodingResponse;
import org.vedant.model.WeatherInfo;
import org.vedant.model.WeatherLocalModel;
import org.vedant.repository.WeatherLocalRepository;
import org.vedant.service.GeocodingService;
import org.vedant.service.OpenWeatherMapService;
import org.vedant.service.WeatherService;
import org.vedant.util.DateUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherLocalRepository weatherRepository;

    @Mock
    private GeocodingService geocodingService;

    @Mock
    private OpenWeatherMapService openWeatherMapService;

    @Mock
    private DateUtil dateUtil;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(weatherRepository, geocodingService, openWeatherMapService, dateUtil);
    }

    @Test
    public void testGetWeatherInfo_WeatherInfoNotFoundLocally_GeocodingSuccess_OpenWeatherMapFailure() {
        // Mocking
        String pincode = "12345";
        String date = "2024-04-24";
        long epochDate = 17383; // Example epoch date
        GeocodingResponse mockGeocodingResponse = new GeocodingResponse();
        mockGeocodingResponse.setLat(10.123);
        mockGeocodingResponse.setLon(20.456);
        when(dateUtil.getEpochFromDate(date)).thenReturn(epochDate);
        when(weatherRepository.findByPincodeAndDate(pincode, epochDate)).thenReturn(null);
        when(geocodingService.getCoordinates(pincode)).thenReturn(mockGeocodingResponse);
        when(openWeatherMapService.getWeather(mockGeocodingResponse.getLat(), mockGeocodingResponse.getLon(), epochDate)).thenReturn(null);

        // Testing
        try {
            weatherService.getWeatherInfo(pincode, date);
        } catch (RuntimeException e) {
            // Assertions
            assertEquals("Failed to get weather information from OpenWeatherMap API", e.getMessage());
            verify(weatherRepository, times(1)).findByPincodeAndDate(pincode, epochDate);
            verify(geocodingService, times(1)).getCoordinates(pincode);
            verify(openWeatherMapService, times(1)).getWeather(mockGeocodingResponse.getLat(), mockGeocodingResponse.getLon(), epochDate);
            verify(weatherRepository, never()).save(any(WeatherLocalModel.class)); // Ensure save method is not called
        }
    }

    @Test
    public void testGetWeatherInfo_WeatherInfoNotFoundLocally_GeocodingFailure() {
        // Mocking
        String pincode = "12345";
        String date = "2024-04-24";
        long epochDate = 17383; // Example epoch date
        when(dateUtil.getEpochFromDate(date)).thenReturn(epochDate);
        when(weatherRepository.findByPincodeAndDate(pincode, epochDate)).thenReturn(null);
        when(geocodingService.getCoordinates(pincode)).thenReturn(null);

        // Testing
        try {
            weatherService.getWeatherInfo(pincode, date);
        } catch (RuntimeException e) {
            // Assertions
            assertEquals("Failed to get coordinates from geocoding API", e.getMessage());
            verify(weatherRepository, times(1)).findByPincodeAndDate(pincode, epochDate);
            verify(geocodingService, times(1)).getCoordinates(pincode);
            verify(openWeatherMapService, never()).getWeather(anyDouble(), anyDouble(), anyLong()); // Ensure OpenWeatherMap service is not called
            verify(weatherRepository, never()).save(any(WeatherLocalModel.class)); // Ensure save method is not called
        }
    }

}

