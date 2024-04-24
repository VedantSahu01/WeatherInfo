package org.vedant.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;

@Component
public class DateUtil {

    public long getEpochFromDate(String forDate) {
        LocalDate date = LocalDate.now();
        if (!Objects.equals(forDate, "")) {
            date = LocalDate.parse(forDate);
        }
        return date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
    }
}
