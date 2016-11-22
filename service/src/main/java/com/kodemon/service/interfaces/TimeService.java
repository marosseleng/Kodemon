package com.kodemon.service.interfaces;

import java.util.Date;

/**
 * A service providing useful time helper methods
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface TimeService {
    /**
     * Returns current {@link Date}
     *
     * @return instance of {@link Date} representing current system time
     */
    Date currentDate();

    /**
     * Returns {@link Date} instance representing the start of the given day
     *
     * @param day instance of {@link Date}, whose start we want
     * @return an instance of {@link Date} representing the start of the given day
     */
    Date startOfTheDay(Date day);

    /**
     * Returns {@link Date} instance representing the end of the given day
     *
     * @param day instance of {@link Date}, whose end we want
     * @return an instance of {@link Date} representing the end of the given day
     */
    Date endOfTheDay(Date day);
}
