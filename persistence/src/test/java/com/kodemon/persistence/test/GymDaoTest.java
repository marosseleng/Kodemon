package com.kodemon.persistence.test;

import com.kodemon.persistence.config.PersistenceConfig;
import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
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
 * Unit tests to test the {@link GymDao}
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class GymDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private GymDao gymDao;

    @Inject
    private TrainerDao trainerDao;

    private Trainer trainer;
    private Gym gym;

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
        gym = null;
    }

    /** save(...) tests */

    @Test(expectedExceptions = {DataAccessException.class})
    void testSaveNullGym() {
        gym = null;
        assertThat(gym, is(nullValue(Gym.class)));
        gymDao.save(gym);
    }

    @Test
    void testSaveCorrectInstance() {
        gym = randomGym(1);
        gym.setCity("Thunder city");
        gym.setType(PokemonType.GRASS);
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
        gym = new Gym(trainer);
        gym.setType(PokemonType.GRASS);
        gym.setCity(null);
        gymDao.save(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveGymWithNullTrainer() {
        gym = new Gym();
        gym.setTrainer(null);
        gym.setType(PokemonType.GRASS);
        gym.setCity("some city");
        gymDao.save(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveGymWithNullTrainerAndNullCity() {
        gym = new Gym();
        gym.setTrainer(null);
        gym.setCity(null);
        gym.setType(PokemonType.GRASS);
        gymDao.save(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveGymWithNullType() {
        gym = new Gym();
        gym.setTrainer(trainer);
        gym.setCity("some city");
        gym.setTrainer(null);
        gymDao.save(gym);
    }

    /** delete(...) tests */

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteNullGym() {
        gym = null;
        assertThat(gym, is(nullValue(Gym.class)));
        gymDao.delete(gym);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteWithNotExistingId() {
        gymDao.delete(123456789L);
    }

    @Test
    void testDeleteByPassingInstance() {
        gym = randomGym(6);
        assertThat(gymDao.count(), is(equalTo(0L)));
        gymDao.save(gym);
        assertThat(gymDao.count(), is(equalTo(1L)));
        assertThat("gym.getId() == null", gym.getId(), is(notNullValue(Long.class)));
        gymDao.delete(gym);
        assertThat(gymDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteByPassingId() {
        gym = randomGym(4);
        assertThat(gymDao.count(), is(equalTo(0L)));
        gymDao.save(gym);
        assertThat(gymDao.count(), is(equalTo(1L)));
        assertThat("gym.getId() == null", gym.getId(), is(notNullValue(Long.class)));
        gymDao.delete(gym.getId());
        assertThat(gymDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteMultipleInstances() {
        int gymsNumber = 5;
        List<Gym> gyms = randomGyms(gymsNumber);
        assertThat(gymDao.count(), is(equalTo(0L)));
        gymDao.save(gyms);
        assertThat(gymDao.count(), is(equalTo((long) gymsNumber)));
        gymDao.delete(gyms);
        assertThat(gymDao.count(), is(equalTo(0L)));
    }

    /** find(...) tests */

    @Test
    void testFindByPassingId() {
        gym = randomGym(6);
        gymDao.save(gym);
        assertThat(gym.getId(), is(notNullValue()));
        Gym found = gymDao.findOne(gym.getId());
        assertThat(found, is(equalTo(gym)));
    }

    @Test
    void testFindByPassingExample() {
        gym = randomGym(6);
        gymDao.save(gym);
        assertThat(gym.getId(), is(notNullValue()));
        Gym exampleGym = new Gym(gym.getTrainer());
        exampleGym.setType(gym.getType());
        exampleGym.setCity(gym.getCity());
        Gym found = gymDao.findOne(Example.of(exampleGym));
        assertThat(found, is(equalTo(gym)));
    }

    @Test
    void testFindAll() {
        int numberOfGyms = 10;
        assertThat(gymDao.findAll(), is(empty()));
        List<Gym> gyms = randomGyms(numberOfGyms);
        gymDao.save(gyms);
        List<Gym> found = gymDao.findAll();
        assertThat(found.size(), is(equalTo(numberOfGyms)));
        assertThat(found, is(equalTo(gyms)));
    }
    
    @Test
    void testFindByCity() {
        List<Gym> gyms = randomGyms(3);
        gymDao.save(gyms);
        assertThat(gymDao.count(), is(equalTo(3L)));
        Gym matching = gyms.get(0);
        List<Gym> found = gymDao.findByCity(matching.getCity());
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(equalTo(matching)));
    }

    @Test
    void testFindByCityLike() {
        List<Gym> gyms = randomGyms(3);
        gymDao.save(gyms);
        assertThat(gymDao.count(), is(equalTo(3L)));
        Gym matching = gyms.get(0);
        List<Gym> found = gymDao.findByCityLike(matching.getCity());
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(equalTo(matching)));
    }

    @Test
    void testFindByCityContaining() {
        List<Gym> gyms = randomGyms(3);
        gymDao.save(gyms);
        assertThat(gymDao.count(), is(equalTo(3L)));
        Gym matching = gyms.get(0);
        List<Gym> found = gymDao.findByCityContaining("%" + matching.getCity().substring(2, 3) + "%");
        assertThat(found.size(), is(3));
        assertThat(found.get(0), is(equalTo(matching)));
    }
    
    @Test
    void testFindByType() {
        List<Gym> gyms = randomGyms(3);
        gyms.get(0).setType(PokemonType.GHOST);
        gyms.get(1).setType(PokemonType.FIRE);
        gyms.get(2).setType(PokemonType.WATER);
        gymDao.save(gyms);
        assertThat(gymDao.count(), is(equalTo(3L)));
        Gym matching = gyms.get(1);
        List<Gym> found = gymDao.findByType(matching.getType());
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(equalTo(matching)));
    }
    
    @Test
    void testFindByTrainer() {
        List<Gym> gyms = randomGyms(4);
        gymDao.save(gyms);
        assertThat(gymDao.count(), is(equalTo(4L)));
        Gym matching = gyms.get(0);
        List<Gym> found = gymDao.findByTrainer(matching.getTrainer());
        assertThat(found.size(), is(4));
        assertThat(found.get(0), is(equalTo(matching)));
    }

    /** update tests */

    @Test
    void testUpdateCity() {
        gym = new Gym(trainer);
        gym.setCity("Some City");
        gym.setBadgeName("Very Good Badge");
        gym.setType(PokemonType.DRAGON);
        gymDao.saveAndFlush(gym);
        String newCity = "another";
        assertThat(gymDao.count(), is(equalTo(1L)));
        gym.setCity(newCity);
        gymDao.saveAndFlush(gym);
        Gym found = gymDao.findOne(gym.getId());
        assertThat(found.getCity(), equalTo(newCity));
    }

    @Test
    void testUpdateTrainer() {
        gym = randomGym(6);
        gymDao.saveAndFlush(gym);
        assertThat(gymDao.count(), is(equalTo(1L)));
        Trainer t = new Trainer();
        Date dob = new Calendar.Builder().setDate(1999, 6, 22).build().getTime();
        t.setDateOfBirth(dob);
        t.setFirstName("Ash");
        t.setLastName("Horcica");
        t.setUserName("ash.horcica");
        trainerDao.saveAndFlush(t);
        assertThat(trainerDao.count(), is(equalTo(2L)));
        gym.setTrainer(t);
        gymDao.saveAndFlush(gym);
        assertThat(gymDao.count(), is(equalTo(1L)));
        assertThat(gymDao.findOne(gym.getId()).getTrainer(), is(equalTo(t)));
    }

    @Test
    void testUpdateType() {
        gym = new Gym(trainer);
        gym.setCity("Rainbow");
        gym.setType(PokemonType.GRASS);
        gym.setBadgeName("KappaPride Badge");
        gymDao.saveAndFlush(gym);
        assertThat(gymDao.count(), is(equalTo(1L)));
        PokemonType newType = PokemonType.DRAGON;
        gym.setType(newType);
        gymDao.saveAndFlush(gym);
        assertThat(gymDao.count(), is(equalTo(1L)));
        assertThat(gymDao.findOne(gym.getId()).getType(), is(equalTo(newType)));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullCity() {
        gym = randomGym(10);
        assertThat(gymDao.count(), is(equalTo(0L)));
        gymDao.save(gym);
        gym.setCity(null);
        gymDao.saveAndFlush(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullPokemonType() {
        gym = randomGym(10);
        assertThat(gymDao.count(), is(equalTo(0L)));
        gymDao.save(gym);
        gym.setType(null);
        gymDao.saveAndFlush(gym);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullTrainer() {
        gym = randomGym(10);
        assertThat(gymDao.count(), is(equalTo(0L)));
        gymDao.save(gym);
        gym.setTrainer(null);
        gymDao.saveAndFlush(gym);
    }

    private void prepareTrainer() {
        trainer = new Trainer();
        trainer.setUserName("ash.ketchum");
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
        gym.setBadgeName(chars.substring(index % chars.length()) + " Badge");
        return gym;
    }
}
