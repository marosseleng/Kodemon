package com.kodemon.service.implementations;

import com.kodemon.service.interfaces.TimeService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.kodemon.service.util.TimeUtils.asDate;
import static com.kodemon.service.util.TimeUtils.asLocalDate;

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
        return asDate(asLocalDate(day).atStartOfDay());
    }

    @Override
    public Date endOfTheDay(Date day) {
        return asDate(asLocalDate(day).atStartOfDay().plusDays(1L));
    }
}
