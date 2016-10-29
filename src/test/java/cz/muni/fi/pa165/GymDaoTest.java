package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.GymDao;
import cz.muni.fi.pa165.kodemon.dao.TrainerDao;
import cz.muni.fi.pa165.kodemon.entity.Gym;
import cz.muni.fi.pa165.kodemon.entity.Trainer;
import cz.muni.fi.pa165.kodemon.enums.PokemonType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by mseleng on 10/25/16.
 */
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class GymDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private GymDao gymDao;

    @Inject
    private TrainerDao trainerDao;

    private Trainer trainer;

    @BeforeMethod
    void prepare() {
        assertThat(gymDao, is(notNullValue(GymDao.class)));
        gymDao.deleteAll();
        assertThat("gymDao.count() != 0L", gymDao.count(), is(0L));
        assertThat(trainerDao, is(notNullValue(TrainerDao.class)));
        trainerDao.deleteAll();
        assertThat("trainerDao.count() != 0L", trainerDao.count(), is(0L));
        prepareTrainer();
        assertThat("trainer == null", trainer, is(notNullValue()));
        trainerDao.save(trainer);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    void testSaveNullGym() {
        Gym gym = null;
        assertThat(gym, is(nullValue(Gym.class)));
        gymDao.save(gym);
    }

    @Test
    void testSaveCorrectInstance() {
        Gym gym = new Gym(trainer);
        gym.setCity("Thunder city");
        gym.setType(PokemonType.EARTH);
        gymDao.save(gym);
        assertThat("gym.getId() == null", gym.getId(), is(notNullValue(Long.class)));
        Gym testGym = gymDao.findOne(gym.getId());
        assertThat(testGym, is(equalTo(gym)));
    }

    @Test
    void testSaveMultipleCorrectInstances() {
        int numberOfGyms = 5;
        List<Gym> gyms = randomGyms(numberOfGyms);
        assertThat(gyms.size(), is(equalTo(numberOfGyms)));
        gymDao.save(gyms);
        assertThat(gymDao.count(), is(equalTo((long) gyms.size())));
        assertThat(gymDao.findAll(), is(equalTo(gyms)));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveGymWithNullCity() {
        Gym gym = new Gym(trainer);
        gym.setType(PokemonType.EARTH);
        gymDao.save(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveGymWithNullTrainer() {
        Gym gym = new Gym();
        gym.setType(PokemonType.EARTH);
        gym.setCity("some city");
        gymDao.save(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveGymWithNullTrainerAndNullCity() {
        Gym gym = new Gym();
        gym.setType(PokemonType.EARTH);
        gymDao.save(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveGymWithoutType() {
        Gym gym = new Gym();
        gym.setTrainer(trainer);
        gym.setCity("some city");
        gymDao.save(gym);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteNullGym() {
        Gym gym = null;
        assertThat(gym, is(nullValue(Gym.class)));
        gymDao.delete(gym);
    }

    @Test
    void testDeleteNotSavedGym() {
        assertThat(gymDao.count(), is(equalTo(0L)));
        Gym gym = new Gym(trainer);
        gym.setCity("Some city");
        gym.setType(PokemonType.EARTH);
        Gym foundGym = gymDao.findOne(Example.of(gym));
        assertThat(foundGym, is(nullValue(Gym.class)));
    }

    private void prepareTrainer() {
        trainer = new Trainer();
        trainer.setFirstName("Ash");
        trainer.setLastName("Ketchum");
        // 22.06.1994
        Date dob = new Calendar.Builder().setDate(1994, 6, 22).build().getTime();
        trainer.setDateOfBirth(dob);
    }

    private List<Gym> randomGyms(int size) {
        List<Gym> gyms = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            gyms.add(randomGym(i));
        }
        assertThat(gyms.size(), is(equalTo(size)));
        return gyms;
    }

    private Gym randomGym(int index) {
        Gym gym = new Gym(trainer);
        int numberOfTypes = PokemonType.values().length;
        assertThat(numberOfTypes, is(greaterThan(0)));
        gym.setType(PokemonType.values()[index % numberOfTypes]);
        String chars = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ ";
        gym.setCity(chars.substring(index % chars.length()));
        return gym;
    }
}
