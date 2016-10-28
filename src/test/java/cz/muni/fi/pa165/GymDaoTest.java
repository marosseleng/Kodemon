package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.GymDao;
import cz.muni.fi.pa165.kodemon.dao.TrainerDao;
import cz.muni.fi.pa165.kodemon.entity.Gym;
import cz.muni.fi.pa165.kodemon.entity.Trainer;
import cz.muni.fi.pa165.kodemon.enums.PokemonType;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

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

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteNullGym() {
        Gym gym = null;
        assertThat(gym, is(nullValue(Gym.class)));
        gymDao.delete(gym);
    }

    @Test
    void testSaveCorrectInstance() {
        Gym gym = new Gym(trainer);
        gym.setCity("Thunder city");
        gym.setType(PokemonType.EARTH);
        gymDao.save(gym);
        assertThat("gym.getId() == null", gym.getId(), is(notNullValue(Long.class)));
        Gym testGym = gymDao.findOne(gym.getId());
        gym.equals(testGym);
//        assertThat(, is(true));
    }

    private void prepareTrainer() {
        trainer = new Trainer();
        trainer.setFirstName("Ash");
        trainer.setLastName("Ketchum");
        // 22.06.1994
        Date dob = new Calendar.Builder().setDate(1994, 6, 22).build().getTime();
        trainer.setDateOfBirth(dob);
    }
}
