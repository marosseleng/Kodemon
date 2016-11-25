package com.kodemon.service.test;

import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.GymService;
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
 * Test class for Gym service
 *
 * @author Matej Poklemba
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class GymServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private GymDao gymDao;

    @Inject
    @InjectMocks
    private GymService gymService;

    private Trainer trainer;
    private Gym gym;
    private String gymcity = "Kosice";

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
        gym.setCity(gymcity);
        gym.setType(PokemonType.WATER);
        gym.setTrainer(trainer);
    }

    @AfterMethod
    public void reset_mocks() {
        Mockito.reset(gymDao);
    }

    @Test
    public void findByCityTest() {
        when(gymDao.findByCity(gymcity)).thenReturn(Collections.singletonList(gym));

        List<Gym> result = gymService.findByCity(gymcity);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(gym));

        verify(gymDao).findByCity(gymcity);
    }

    @Test
    public void findByCityLikeTest() {
        when(gymDao.findByCityLike(gymcity.substring(0, 3))).thenReturn(Collections.singletonList(gym));

        List<Gym> result = gymService.findByCityLike(gymcity.substring(0, 3));
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(gym));

        verify(gymDao).findByCityLike(gymcity.substring(0, 3));
    }

    @Test
    public void findByCityContainingTest() {
        when(gymDao.findByCityContaining(gymcity.substring(0, 3) + "%")).thenReturn(Collections.singletonList(gym));

        List<Gym> result = gymService.findByCityContaining(gymcity.substring(0, 3) + "%");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(gym));

        verify(gymDao).findByCityContaining(gymcity.substring(0, 3) + "%");
    }

    @Test
    public void findByTypeTest() {
        when(gymDao.findByType(gym.getType())).thenReturn(Collections.singletonList(gym));

        List<Gym> result = gymService.findByType(gym.getType());
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(gym));

        verify(gymDao).findByType(gym.getType());
    }

    @Test
    public void findByTrainerTest() {
        when(gymDao.findByTrainer(trainer)).thenReturn(Collections.singletonList(gym));

        List<Gym> result = gymService.findByTrainer(trainer);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(gym));

        verify(gymDao).findByTrainer(trainer);
    }

    @Test
    public void findAllTest() {
        when(gymDao.findAll()).thenReturn(Collections.singletonList(gym));

        List<Gym> result = gymService.findAll();
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(gym));

        verify(gymDao).findAll();
    }

    @Test
    public void successfulSaveTest() {
        when(gymDao.save(gym)).thenReturn(gym);
        gymService.save(gym);
        verify(gymDao).save(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveNullObjectTest() {
        gym = null;
        when(gymDao.save(gym)).thenThrow(NullPointerException.class);
        gymService.save(gym);
        verify(gymDao).save(gym);
    }

    @Test
    public void successfulDeleteTest() {
        doNothing().when(gymDao).delete(gym);
        gymService.delete(gym);
        verify(gymDao).delete(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void nullDeleteTest() {
        Gym g = null;
        doThrow(NullPointerException.class).when(gymDao).delete(g);
        gymService.delete(g);
    }
}
