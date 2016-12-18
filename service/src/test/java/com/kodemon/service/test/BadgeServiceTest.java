package com.kodemon.service.test;

import com.kodemon.persistence.dao.BadgeDao;
import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.BadgeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

/**
 * Test class for Badge service
 *
 * @author Matej Poklemba
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class BadgeServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private BadgeDao badgeDao;

    @Inject
    @InjectMocks
    private BadgeService badgeService;

    private Trainer trainer;
    private Gym gym;
    private Badge badge;
    private String badgename = "Best Badge";

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepare() {
        trainer = new Trainer();
        trainer.setUserName("brock1999");
        trainer.setFirstName("Brock");
        trainer.setLastName("Brockowski");
        Date born = new Calendar.Builder().setDate(1999, 5, 5).build().getTime();
        trainer.setDateOfBirth(born);

        gym = new Gym(trainer);
        gym.setCity("Saffron City");
        gym.setType(PokemonType.WATER);
        gym.setTrainer(trainer);

        badge = new Badge(gym);
        badge.setName(badgename);
    }

    @AfterMethod
    public void reset_mocks() {
        Mockito.reset(badgeDao);
    }

    @Test
    public void createBadgeOfGymTest() {
        Badge result = badgeService.createBadgeOfGym(gym);
        assertThat("badge == null", result, is(notNullValue()));
        assertThat(result.getGym(), is(gym));
    }

    @Test
    public void findByNameTest() {
        when(badgeDao.findByName(badgename)).thenReturn(Collections.singletonList(badge));

        List<Badge> result = badgeService.findByName(badgename);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(badge));

        verify(badgeDao).findByName(badgename);
    }

    @Test
    public void findByNameStartingWithTest() {
        when(badgeDao.findByNameStartingWith(badgename.substring(0, 3) + "%")).thenReturn(Collections.singletonList(badge));

        List<Badge> result = badgeService.findByNameStartingWith(badgename.substring(0, 3) + "%");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(badge));

        verify(badgeDao).findByNameStartingWith(badgename.substring(0, 3) + "%");
    }

    @Test
    public void findByGymTest() {
        when(badgeDao.findByGym(gym)).thenReturn(Collections.singletonList(badge));

        List<Badge> result = badgeService.findByGym(gym);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(badge));

        verify(badgeDao).findByGym(gym);
    }

    @Test
    public void successfulSaveTest() {
        when(badgeDao.save(badge)).thenReturn(badge);
        badgeService.save(badge);
        verify(badgeDao).save(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveNullObjectTest() {
        badge = null;
        when(badgeDao.save(badge)).thenThrow(NullPointerException.class);
        badgeService.save(badge);
        verify(badgeDao).save(badge);
    }

    @Test
    public void successfulDeleteTest() {
        doNothing().when(badgeDao).delete(badge);
        badgeService.delete(badge);
        verify(badgeDao).delete(badge);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void nullDeleteTest() {
        Badge b = null;
        doThrow(NullPointerException.class).when(badgeDao).delete(b);
        badgeService.delete(b);
    }
}
