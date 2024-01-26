package com.polimi.ckb.timeServer.utility;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeTypeConverter {

    public static long calculateMillisecondsToDeadline(String deadlineIsoDate) {
        LocalDateTime deadline = LocalDateTime.parse(deadlineIsoDate);
        Instant deadlineInstant = deadline.atZone(ZoneId.systemDefault()).toInstant();
        Instant now = Instant.now();

        Duration duration = Duration.between(now, deadlineInstant);
        return duration.toMillis();
    }
}
