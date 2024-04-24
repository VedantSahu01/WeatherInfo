package org.vedant.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class WeatherInfoResponse {
    private String message;

    private String cod;

    private long city_id;

    private double calctime;

    private int cnt;
    @ElementCollection
    private List<WeatherInfo> list;
}
