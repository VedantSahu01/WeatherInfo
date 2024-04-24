package org.vedant.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Weather {
    private String main;
    private String description;
    private String icon;
}
