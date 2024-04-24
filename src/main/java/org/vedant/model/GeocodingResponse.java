package org.vedant.model;

import lombok.Data;

@Data
public class GeocodingResponse {
    private String zip;

    private String name;

    private double lat;

    private double lon;

    private String country;
}
