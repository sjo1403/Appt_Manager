package model;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeCalculations {

    public static String localToUTC(LocalDateTime dateLocal) throws ParseException {
        //convert to UTC
        ZonedDateTime ldtZoned = dateLocal.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateZoned = FORMAT.format(utcZoned);

        System.out.println(dateZoned);
        return dateZoned;
    }

}
