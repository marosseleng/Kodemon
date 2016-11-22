package com.kodemon.service.implementations;

import com.kodemon.service.interfaces.TimeService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Implementation of the TimeService
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Service
public class TimeServiceImpl implements TimeService {
    @Override
    public Date currentDate() {
        return new Date();
    }

    @Override
    public Date startOfTheDay(Date day) {
        DateTime dt = new DateTime(day);
        return dt.withTimeAtStartOfDay().toDate();
    }

    @Override
    public Date endOfTheDay(Date day) {
        DateTime dt = new DateTime(day);
        return dt.plusDays(1).withTimeAtStartOfDay().toDate();
    }
}
