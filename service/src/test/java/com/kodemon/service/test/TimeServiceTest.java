package com.kodemon.service.test;

import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.TimeService;
import org.joda.time.DateTime;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Tests for time service.
 *
 * @author Miso Romanek
 */

@ContextConfiguration(classes=ServiceConfig.class)
public class TimeServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Inject
    private TimeService timeService;

    @Test
    public void currentDateTest() {
        assertThat(timeService.currentDate(), is(equalTo(new Date())));
    }

    @Test
    public void startOfTheDayTest() {
        DateTime dt = new DateTime(new Date());
        assertThat(timeService.startOfTheDay(new Date()), equalTo(dt.withTimeAtStartOfDay().toDate()));
    }

    @Test
    public void endOfTheDayTest() {
        DateTime dt = new DateTime(new Date());
        assertThat(timeService.endOfTheDay(new Date()), equalTo(dt.plusDays(1).withTimeAtStartOfDay().toDate()));
    }
}
