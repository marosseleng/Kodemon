package com.kodemon.service;

import java.util.Date;

/**
 * Created by mseleng on 11/21/16.
 */
public interface TimeService {
    /**
     * Returns current Date
     *
     * @return
     */
    Date currentDate();

    /**
     * Returns Date instance representing the start of the given day
     *
     * @param day
     * @return
     */
    Date startOfTheDay(Date day);

    /**
     * Returns Date instance representing the end of the given day
     *
     * @param day
     * @return
     */
    Date endOfTheDay(Date day);
}
