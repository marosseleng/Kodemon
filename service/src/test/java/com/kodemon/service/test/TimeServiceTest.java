package com.kodemon.service.test;

import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.TimeService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import static com.kodemon.service.util.TimeUtils.asDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * Tests for time service.
 *
 * @author Miso Romanek
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class TimeServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final long ALLOWED_RESERVE_MILLIS = 5L;

    @Inject
    private TimeService timeService;

    @Test
    public void currentDateTest() {
        long myDate = new Date().getTime();
        long serviceDate = timeService.currentDate().getTime();
        assertThat(Math.abs(myDate - serviceDate), is(lessThanOrEqualTo(ALLOWED_RESERVE_MILLIS)));
    }

    @Test
    public void startOfTheDayTest() {
        LocalDate localDate = LocalDate.of(2016, Month.DECEMBER, 12);
        long myTime = asDate(localDate.atStartOfDay()).getTime();
        long serviceTime = timeService.startOfTheDay(asDate(localDate.atStartOfDay())).getTime();
        assertThat(Math.abs(serviceTime - myTime), is(lessThanOrEqualTo(ALLOWED_RESERVE_MILLIS)));
    }

    @Test
    public void endOfTheDayTest() {
        LocalDate localDate = LocalDate.of(2016, Month.DECEMBER, 12);
        long myTime = asDate(localDate.atStartOfDay().plusDays(1L)).getTime();
        long serviceTime = timeService.endOfTheDay(asDate(localDate.atStartOfDay())).getTime();
        assertThat(Math.abs(serviceTime - myTime), is(lessThanOrEqualTo(ALLOWED_RESERVE_MILLIS)));
    }
}
