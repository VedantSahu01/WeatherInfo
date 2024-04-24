package org.vedant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Rain {
    @Column(name= "rain_all")
    private String all;
}
