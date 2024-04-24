package org.vedant.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Temperature {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;
    private int sea_level;
    private int grnd_level;
}
