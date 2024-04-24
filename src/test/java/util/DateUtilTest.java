package util;
import org.junit.jupiter.api.Test;
import org.vedant.util.DateUtil;

import java.time.LocalDate;
import java.time.ZoneOffset;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilTest {

    private final DateUtil dateUtil = new DateUtil();

    @Test
    public void testGetEpochFromDate_CurrentDate() {
        // Testing with current date
        long expectedEpoch = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        long actualEpoch = dateUtil.getEpochFromDate("");
        assertEquals(expectedEpoch, actualEpoch);
    }

    @Test
    public void testGetEpochFromDate_CustomDate() {
        // Testing with a custom date
        String customDate = "2024-04-24";
        long expectedEpoch = LocalDate.parse(customDate).atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        long actualEpoch = dateUtil.getEpochFromDate(customDate);
        assertEquals(expectedEpoch, actualEpoch);
    }

    @Test
    public void testGetEpochFromDate_EmptyString() {
        // Testing with an empty string
        long expectedEpoch = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        long actualEpoch = dateUtil.getEpochFromDate("");
        assertEquals(expectedEpoch, actualEpoch);
    }
}
