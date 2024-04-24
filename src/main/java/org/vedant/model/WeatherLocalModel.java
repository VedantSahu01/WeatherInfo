package org.vedant.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "weather_info_a")
public class WeatherLocalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pincode;
    private String latitude;
    private String longitude;
    private long date;
    private boolean isLocal;
    @Embedded
    private WeatherInfo weatherInfo;
}
