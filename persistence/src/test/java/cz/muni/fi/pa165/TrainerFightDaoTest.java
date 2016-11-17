package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.TrainerFightDao;
import cz.muni.fi.pa165.kodemon.dao.TrainerDao;
import cz.muni.fi.pa165.kodemon.dao.GymDao;
import cz.muni.fi.pa165.kodemon.entity.TrainerFight;
import cz.muni.fi.pa165.kodemon.entity.Trainer;
import cz.muni.fi.pa165.kodemon.entity.Gym;
import cz.muni.fi.pa165.kodemon.enums.PokemonType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
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
 * Unit tests of {@link TrainerFightDao}
 *
 * @author Miso Romanek
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class TrainerFightDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private TrainerFightDao trainerFightDao;

    @Inject
    private TrainerDao trainerDao;

    @Inject
    private GymDao gymDao;

    private TrainerFight trainerFight;
    private Trainer trainer;
    private Gym gym;

    @BeforeMethod
    void prepare() {
        assertThat(trainerFightDao, is(notNullValue(TrainerFightDao.class)));
        trainerFightDao.deleteAll();
        assertThat("trainerFightDao.count() != 0L", trainerFightDao.count(), is(0L));

        assertThat(trainerDao, is(notNullValue(TrainerDao.class)));
        trainerDao.deleteAll();
        assertThat("trainerDao.count() != 0L", trainerDao.count(), is(0L));

        assertThat(gymDao, is(notNullValue(GymDao.class)));
        gymDao.deleteAll();
        assertThat("gymDao.count() != 0L", gymDao.count(), is(0L));

        prepareTrainer();
        assertThat("trainer == null", trainer, is(notNullValue()));
        trainerDao.save(trainer);

        prepareGym();
        assertThat("gym == null", gym, is(notNullValue()));
        gymDao.save(gym);

        trainerFight = null;
    }

    /** save(...) tests */

    @Test(expectedExceptions = {DataAccessException.class})
    void testSaveNullTrainerFight() {
        trainerFight = null;
        assertThat(trainerFight, is(nullValue(TrainerFight.class)));
        trainerFightDao.save(trainerFight);
    }

    @Test
    void testSaveCorrectInstance() {
        trainerFight = randomTrainerFight(1);
        trainerFightDao.save(trainerFight);
        assertThat("trainer.getId() == null", trainer.getId(), is(notNullValue(Long.class)));
        assertThat("gym.getId() == null", gym.getId(), is(notNullValue(Long.class)));
        TrainerFight testTrainerFight = trainerFightDao.findOne(trainerFight.getId());
        assertThat(testTrainerFight, is(equalTo(trainerFight)));
    }

    @Test
    void testSaveMultipleCorrectInstances() {
        int numberOfTrainerFights = 3;
        List<TrainerFight> trainerFights = randomTrainerFights(numberOfTrainerFights);
        assertThat(trainerFights.size(), is(equalTo(numberOfTrainerFights)));
        trainerFightDao.save(trainerFights);
        assertThat(trainerFightDao.count(), is(equalTo((long) trainerFights.size())));
        assertThat(trainerFightDao.findAll(), is(equalTo(trainerFights)));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerFightWithNullGym() {
        trainerFight = new TrainerFight();
        trainerFight.setTargetGym(null);
        trainerFight.setChallenger(trainer);
        trainerFight.setWasChallengerSuccessful(true);
        trainerFightDao.save(trainerFight);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerFightWithNullTrainer() {
        trainerFight = new TrainerFight();
        trainerFight.setTargetGym(gym);
        trainerFight.setChallenger(null);
        trainerFight.setWasChallengerSuccessful(true);
        trainerFightDao.save(trainerFight);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerFightWithNullTrainerAndNullGym() {
        trainerFight = new TrainerFight();
        trainerFight.setTargetGym(null);
        trainerFight.setChallenger(null);
        trainerFight.setWasChallengerSuccessful(true);
        trainerFightDao.save(trainerFight);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerFightWithNullFightTime() {
        trainerFight = new TrainerFight();
        trainerFight.setTargetGym(gym);
        trainerFight.setChallenger(trainer);
        trainerFight.setFightTime(null);
        trainerFight.setWasChallengerSuccessful(true);
        trainerFightDao.save(trainerFight);
    }


    /** delete(...) tests */

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteNullTrainerFight() {
        trainerFight = null;
        assertThat(trainerFight, is(nullValue(TrainerFight.class)));
        trainerFightDao.delete(trainerFight);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteWithNonexistentId() {
        trainerFightDao.delete(99999999999L);
    }

    @Test
    void testDeleteByPassingInstance() {
        trainerFight = randomTrainerFight(5);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
        trainerFightDao.save(trainerFight);
        assertThat(trainerFightDao.count(), is(equalTo(1L)));
        assertThat("trainerFight.getId() == null", trainerFight.getId(), is(notNullValue(Long.class)));
        trainerFightDao.delete(trainerFight);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteByPassingId() {
        trainerFight = randomTrainerFight(5);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
        trainerFightDao.save(trainerFight);
        assertThat(trainerFightDao.count(), is(equalTo(1L)));
        assertThat("trainerFight.getId() == null", trainerFight.getId(), is(notNullValue(Long.class)));
        trainerFightDao.delete(trainerFight.getId());
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteMultipleInstances() {
        List<TrainerFight> trainerFights = randomTrainerFights(5);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
        trainerFightDao.save(trainerFights);
        assertThat(trainerFightDao.count(), is(equalTo(5L)));
        trainerFightDao.delete(trainerFights);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
    }

    /** find(...) tests */

    @Test
    void testFindByPassingId() {
        trainerFight = randomTrainerFight(5);
        trainerFightDao.save(trainerFight);
        assertThat(trainerFight.getId(), is(notNullValue()));
        TrainerFight found = trainerFightDao.findOne(trainerFight.getId());
        assertThat(found, is(equalTo(trainerFight)));
    }

    @Test
    void testFindByPassingExample() {
        trainerFight = randomTrainerFight(5);
        trainerFightDao.save(trainerFight);
        assertThat(trainerFight.getId(), is(notNullValue()));
        TrainerFight trainerFightExample = new TrainerFight();
        trainerFightExample.setWasChallengerSuccessful(trainerFight.isWasChallengerSuccessful());
        trainerFightExample.setTargetGym(trainerFight.getTargetGym());
        trainerFightExample.setChallenger(trainerFight.getChallenger());
        TrainerFight found = trainerFightDao.findOne(Example.of(trainerFightExample));
        assertThat(found, is(equalTo(trainerFight)));
    }

    @Test
    void testFindAll() {
        int numberOfTrainerFights = 5;
        assertThat(trainerFightDao.findAll(), is(empty()));
        List<TrainerFight> trainerFights = randomTrainerFights(numberOfTrainerFights);
        trainerFightDao.save(trainerFights);
        List<TrainerFight> found = trainerFightDao.findAll();
        assertThat(found.size(), is(equalTo(numberOfTrainerFights)));
        assertThat(found, is(equalTo(trainerFights)));
    }

    /** update tests */

    @Test
    void testCorrectNameUpdate() {
        trainerFight = randomTrainerFight(5);
        trainerFightDao.save(trainerFight);
        assertThat(trainerFightDao.count(), is(equalTo(1L)));
        trainerFight.setWasChallengerSuccessful(true);
        trainerFightDao.saveAndFlush(trainerFight);
        TrainerFight found = trainerFightDao.findOne(trainerFight.getId());
        assertThat(found, equalTo(trainerFight));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullTrainer() {
        trainerFight = randomTrainerFight(5);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
        trainerFightDao.save(trainerFight);
        trainerFight.setChallenger(null);
        trainerFightDao.saveAndFlush(trainerFight);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullGym() {
        trainerFight = randomTrainerFight(5);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
        trainerFightDao.save(trainerFight);
        trainerFight.setTargetGym(null);
        trainerFightDao.saveAndFlush(trainerFight);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullFightTime() {
        trainerFight = randomTrainerFight(5);
        assertThat(trainerFightDao.count(), is(equalTo(0L)));
        trainerFightDao.save(trainerFight);
        trainerFight.setFightTime(null);
        trainerFightDao.saveAndFlush(trainerFight);
    }

    //Custom find tests

    @Test
    void testFindByChallenger() {
        List<TrainerFight> fights = randomTrainerFights(5);

        Trainer customTrainer = new Trainer();
        customTrainer.setUserName("GaryOak001");
        customTrainer.setFirstName("Gary");
        customTrainer.setLastName("Oak");
        Date dob = new Calendar.Builder().setDate(1993, 4, 13).build().getTime();
        customTrainer.setDateOfBirth(dob);

        trainerDao.save(customTrainer);

        fights.get(4).setChallenger(customTrainer);
        trainerFightDao.save(fights);
        assertThat(trainerFightDao.count(), is(equalTo(5L)));
        TrainerFight matching = fights.get(0);
        List<TrainerFight> found = trainerFightDao.findByChallenger(matching.getChallenger());
        assertThat(found.size(), is(4));
        assertThat(found.get(0), is(equalTo(matching)));
    }

    @Test
    void testFindByTargetGym() {
        List<TrainerFight> fights = randomTrainerFights(5);

        Gym customGym = new Gym(trainer);
        customGym.setCity("Saffron City");
        customGym.setType(PokemonType.WATER);
        customGym.setTrainer(trainer);

        gymDao.save(customGym);

        fights.get(4).setTargetGym(customGym);
        trainerFightDao.save(fights);
        assertThat(trainerFightDao.count(), is(equalTo(5L)));
        TrainerFight matching = fights.get(0);
        List<TrainerFight> found = trainerFightDao.findByTargetGym(matching.getTargetGym());
        assertThat(found.size(), is(4));
        assertThat(found.get(0), is(equalTo(matching)));
    }

    @Test
    void testFindByFightTimeBetween() {
        List<TrainerFight> fights = randomTrainerFights(3);
        trainerFightDao.save(fights);
        assertThat(trainerFightDao.count(), is(equalTo(3L)));
        TrainerFight matching = fights.get(0);
        List<TrainerFight> found = trainerFightDao.findByFightTimeBetween(matching.getFightTime(), new Date());
        assertThat(found.size(), is(3));
        assertThat(found, is(equalTo(fights)));
    }

    private void prepareTrainer() {
        trainer = new Trainer();
        trainer.setFirstName("Ash");
        trainer.setLastName("Ketchum");
        trainer.setUserName("ashhhh");
        Date born = new Calendar.Builder().setDate(1990, 12, 24).build().getTime();
        trainer.setDateOfBirth(born);
    }

    private void prepareGym() {
        gym = new Gym(trainer);
        gym.setCity("Saffron City");
        gym.setType(PokemonType.WATER);
        gym.setTrainer(trainer);
    }

    private List<TrainerFight> randomTrainerFights(int size) {
        List<TrainerFight> trainerFights = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            trainerFights.add(randomTrainerFight(i));
        }
        assertThat(trainerFights.size(), is(equalTo(size)));
        return trainerFights;
    }

    private TrainerFight randomTrainerFight(int index) {
        TrainerFight trainerFight = new TrainerFight();
        trainerFight.setChallenger(trainer);
        trainerFight.setTargetGym(gym);
        Date fightTime = new Calendar.Builder().setDate(1990, 12, (index % 20)+1).build().getTime();
        trainerFight.setFightTime(fightTime);
        trainerFight.setWasChallengerSuccessful((index % 2) == 0);
        return trainerFight;
    }
}
