package model;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeCalculations {

    public static String localDateTimeToUTC(LocalDateTime dateLocal) throws ParseException {
        ZonedDateTime ldtZoned = dateLocal.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateZoned = FORMAT.format(utcZoned);

        return dateZoned;
    }

}
