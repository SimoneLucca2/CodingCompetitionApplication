package com.polimi.ckb.timeServer.utility;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeTypeConverter {

    public static long calculateMillisecondsToDeadline(String dateTimeStr) {
        // Parse the input string to a ZonedDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime targetDateTime = ZonedDateTime.parse(dateTimeStr, formatter);

        // Get the current time in UTC
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        // Calculate the duration between now and the target time
        Duration duration = Duration.between(now, targetDateTime);

        // Return the duration in milliseconds
        return duration.toMillis();
    }
}
