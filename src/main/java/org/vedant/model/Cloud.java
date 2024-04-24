package org.vedant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Cloud {
    @Column(name = "1h")
    private String value;
}
