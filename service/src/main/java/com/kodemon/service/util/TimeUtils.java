package com.kodemon.service.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class providing static method for the conversion of {@link java.util.Date} to
 * {@link java.time.LocalDateTime} and {@link java.time.LocalDate}
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public abstract class TimeUtils {
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
