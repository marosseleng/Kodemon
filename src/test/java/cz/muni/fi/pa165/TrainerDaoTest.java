package cz.muni.fi.pa165;

import cz.muni.fi.pa165.kodemon.KodemonApplicationContext;
import cz.muni.fi.pa165.kodemon.dao.PokemonDao;
import cz.muni.fi.pa165.kodemon.dao.TrainerDao;
import cz.muni.fi.pa165.kodemon.entity.Pokemon;
import cz.muni.fi.pa165.kodemon.entity.Trainer;
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
 * Unit tests to test the {@link TrainerDao}
 *
 * @author Matej Poklemba
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class TrainerDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private PokemonDao pokemonDao;

    @Inject
    private TrainerDao trainerDao;

    private Trainer trainer;
    private Pokemon pokemon;

    @BeforeMethod
    void prepare() {
        assertThat(trainerDao, is(notNullValue(TrainerDao.class)));
        trainerDao.deleteAll();
        assertThat("trainerDao.count() != 0L", trainerDao.count(), is(0L));

        assertThat(pokemonDao, is(notNullValue(PokemonDao.class)));
        pokemonDao.deleteAll();
        assertThat("pokemonDao.count() != 0L", pokemonDao.count(), is(0L));
        preparePokemon();
        assertThat("pokemon == null", pokemon, is(notNullValue()));
        pokemonDao.save(pokemon);

        trainer = null;
    }

    // save(...) tests

    @Test(expectedExceptions = {DataAccessException.class})
    void testSaveNullTrainer() {
        trainer = null;
        assertThat(trainer, is(nullValue(Trainer.class)));
        trainerDao.save(trainer);
    }

    @Test
    void testSaveCorrectInstance() {
        trainer = randomTrainer(1);
        trainer.setFirstName("Ash");
        trainer.setLastName("Ketchum");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        trainer.setDateOfBirth(dob);
        trainerDao.save(trainer);
        assertThat("gym.getId() == null", trainer.getId(), is(notNullValue(Long.class)));
        Trainer testTrainer = trainerDao.findOne(trainer.getId());
        assertThat(testTrainer, is(equalTo(trainer)));
    }

    @Test
    void testSaveMultipleCorrectInstances() {
        int numberOfTrainers = 5;
        List<Trainer> trainers = randomTrainers(numberOfTrainers);
        assertThat(trainers.size(), is(equalTo(numberOfTrainers)));
        trainerDao.save(trainers);
        assertThat(trainerDao.count(), is(equalTo((long) trainers.size())));
        assertThat(trainerDao.findAll(), is(equalTo(trainers)));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerWithNullFirstName() {
        trainer = new Trainer();
        trainer.setLastName("Ketchum");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        trainer.setDateOfBirth(dob);
        trainerDao.save(trainer);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerWithNullLastName() {
        trainer = new Trainer();
        trainer.setFirstName("Ash");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        trainer.setDateOfBirth(dob);
        trainerDao.save(trainer);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerWithNullDateOfBirth() {
        trainer = new Trainer();
        trainer.setFirstName("Ash");
        trainer.setLastName("Ketchum");
        trainerDao.save(trainer);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSaveTrainerWithNullFirstNameAndNullLastName() {
        trainer = new Trainer();
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        trainer.setDateOfBirth(dob);
        trainerDao.save(trainer);
    }

    //delete(...) tests

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteNullTrainer() {
        trainer = null;
        assertThat(trainer, is(nullValue(Trainer.class)));
        trainerDao.delete(trainer);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteTrainerWithNonExistantId() {
        trainerDao.delete(99999L);
    }

    @Test
    void testDeleteByPassingInstance() {
        trainer = randomTrainer(9);
        assertThat(trainerDao.count(), is(equalTo(0L)));
        trainerDao.save(trainer);
        assertThat(trainerDao.count(), is(equalTo(1L)));
        assertThat("trainer.getId() == null", trainer.getId(), is(notNullValue(Long.class)));
        trainerDao.delete(trainer);
        assertThat(trainerDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteByPassingId() {
        trainer = randomTrainer(9);
        assertThat(trainerDao.count(), is(equalTo(0L)));
        trainerDao.save(trainer);
        assertThat(trainerDao.count(), is(equalTo(1L)));
        assertThat("trainer.getId() == null", trainer.getId(), is(notNullValue(Long.class)));
        trainerDao.delete(trainer.getId());
        assertThat(trainerDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteMultipleInstances() {
        int trainersNumber = 10;
        List<Trainer> gyms = randomTrainers(trainersNumber);
        assertThat(trainerDao.count(), is(equalTo(0L)));
        trainerDao.save(gyms);
        assertThat(trainerDao.count(), is(equalTo((long) trainersNumber)));
        trainerDao.delete(gyms);
        assertThat(trainerDao.count(), is(equalTo(0L)));
    }

    // find(...) tests

    @Test
    void testFindByPassingId() {
        trainer = randomTrainer(9);
        trainerDao.save(trainer);
        assertThat(trainer.getId(), is(notNullValue()));
        Trainer found = trainerDao.findOne(trainer.getId());
        assertThat(found, is(equalTo(trainer)));
    }

    @Test
    void testFindByPassingExample() {
        trainer = randomTrainer(9);
        trainerDao.save(trainer);
        assertThat(trainer.getId(), is(notNullValue()));
        Trainer exampleTrainer = new Trainer(trainer.getPokemons().get(0));
        exampleTrainer.setFirstName(trainer.getFirstName());
        exampleTrainer.setLastName(trainer.getLastName());
        exampleTrainer.setDateOfBirth(trainer.getDateOfBirth());
        Trainer found = trainerDao.findOne(Example.of(exampleTrainer));
        assertThat(found, is(equalTo(trainer)));
    }

    @Test
    void testFindAll() {
        int numberOfTrainers = 10;
        assertThat(trainerDao.findAll(), is(empty()));
        List<Trainer> gyms = randomTrainers(numberOfTrainers);
        trainerDao.save(gyms);
        List<Trainer> found = trainerDao.findAll();
        assertThat(found.size(), is(equalTo(numberOfTrainers)));
        assertThat(found, is(equalTo(gyms)));
    }

    // update tests

    @Test
    void testCorrectFirstNameUpdate() {
        trainer = randomTrainer(8);
        trainerDao.save(trainer);
        assertThat(trainerDao.count(), is(equalTo(1L)));
        trainer.setFirstName("Alojz");
        trainerDao.saveAndFlush(trainer);
        Trainer found = trainerDao.findOne(trainer.getId());
        assertThat(found, equalTo(trainer));
    }

    @Test
    void testCorrectLastNameUpdate() {
        trainer = randomTrainer(8);
        trainerDao.save(trainer);
        assertThat(trainerDao.count(), is(equalTo(1L)));
        trainer.setLastName("Alojz");
        trainerDao.saveAndFlush(trainer);
        Trainer found = trainerDao.findOne(trainer.getId());
        assertThat(found, equalTo(trainer));
    }

    @Test
    void testCorrectDateOfBirthUpdate() {
        trainer = randomTrainer(8);
        trainerDao.save(trainer);
        assertThat(trainerDao.count(), is(equalTo(1L)));
        Date dob = new Calendar.Builder().setDate(1994, 10, 10).build().getTime();
        trainer.setDateOfBirth(dob);
        trainerDao.saveAndFlush(trainer);
        Trainer found = trainerDao.findOne(trainer.getId());
        assertThat(found, equalTo(trainer));
    }

    @Test
    void testCorrectPokemonUpdate() {
        trainer = randomTrainer(8);
        trainerDao.save(trainer);
        assertThat(trainerDao.count(), is(equalTo(1L)));
        Trainer found = trainerDao.findOne(trainer.getId());
        assertThat(found.getPokemons().size(), is(equalTo(1)));
        trainer.removePokemon(pokemon);
        trainerDao.saveAndFlush(trainer);
        found = trainerDao.findOne(trainer.getId());
        assertThat(found.getPokemons().size(), is(equalTo(0)));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullFirstName() {
        trainer = randomTrainer(10);
        assertThat(trainerDao.count(), is(equalTo(0L)));
        trainerDao.save(trainer);
        trainer.setFirstName(null);
        trainerDao.saveAndFlush(trainer);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullLastName() {
        trainer = randomTrainer(10);
        assertThat(trainerDao.count(), is(equalTo(0L)));
        trainerDao.save(trainer);
        trainer.setLastName(null);
        trainerDao.saveAndFlush(trainer);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullDateOfBirth() {
        trainer = randomTrainer(10);
        assertThat(trainerDao.count(), is(equalTo(0L)));
        trainerDao.save(trainer);
        trainer.setDateOfBirth(null);
        trainerDao.saveAndFlush(trainer);
    }

    private void preparePokemon() {
        pokemon = new Pokemon();
        pokemon.setName("Pikachu");
        pokemon.setLevel(5);
        pokemon.setType(PokemonType.ELECTRIC);
    }

    private List<Trainer> randomTrainers(int size) {
        List<Trainer> trainers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            trainers.add(randomTrainer(i));
        }
        assertThat(trainers.size(), is(equalTo(size)));
        return trainers;
    }

    private Trainer randomTrainer(int index) {
        Trainer trainer = new Trainer(pokemon);
        String chars = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ ";
        trainer.setFirstName(chars.substring(index % chars.length()));
        trainer.setLastName(chars.substring(index % chars.length()));
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        trainer.setDateOfBirth(dob);
        return trainer;
    }
}