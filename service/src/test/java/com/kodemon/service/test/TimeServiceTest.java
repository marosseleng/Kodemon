package com.kodemon.service.test;

import java.util.Date;

import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.PokemonFightService;
import com.kodemon.service.interfaces.TimeService;
import com.kodemon.service.interfaces.TrainerFightService;
import org.joda.time.DateTime;
import com.kodemon.service.util.Pair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
