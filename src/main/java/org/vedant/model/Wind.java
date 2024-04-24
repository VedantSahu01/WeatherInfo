package org.vedant.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Wind {
    private double speed;
    private int deg;
    private double gust;
}
