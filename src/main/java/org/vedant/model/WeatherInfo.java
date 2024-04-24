package org.vedant.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class WeatherInfo {
    @ElementCollection
    private List<Weather> weather;
    private String base;
    @Embedded
    private Temperature main;
    private int visibility;
    @Embedded
    private Wind wind;
    @Embedded
    private Cloud clouds;
    @Embedded
    private Rain rain;
    private long dt;
}
