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
import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *  @author Oliver Roch
 */

@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = KodemonApplicationContext.class)
public class PokemonDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private PokemonDao pokemonDao;

    @Inject
    private TrainerDao trainerDao;

    private Trainer trainer;

    @BeforeMethod
    void prepare() {
        assertThat(pokemonDao, is(notNullValue(PokemonDao.class)));
        pokemonDao.deleteAll();
        assertThat("pokemonDao.count() != 0L", pokemonDao.count(), is(0L));
        assertThat(trainerDao, is(notNullValue(TrainerDao.class)));
        trainerDao.deleteAll();
        assertThat("trainerDao.count() != 0L", trainerDao.count(), is(0L));
        prepareTrainer();
        assertThat("trainer == null", trainer, is(notNullValue()));
        trainerDao.save(trainer);
    }


    //Save tests

    @Test(expectedExceptions = {DataAccessException.class})
    void testSaveNullPokemon() {
        Pokemon pokemon = null;
        assertThat(pokemon, is(nullValue(Pokemon.class)));
        pokemonDao.save(pokemon);
    }

    @Test
    void testSaveCorrectInstance() {
        Pokemon pokemon = new Pokemon();

        pokemon.setTrainer(trainer);
        pokemon.setName("Eevee");
        pokemon.setLevel(1);
        pokemon.setNickname("Killer");
        pokemon.setType(PokemonType.NORMAL);

        trainer.addPokemon(pokemon);

        pokemonDao.save(pokemon);
        assertThat("pokemon.getId() == null", pokemon.getId(), is(notNullValue(Long.class)));
        Pokemon testPokemon = pokemonDao.findOne(pokemon.getId());
        assertThat(testPokemon, is(equalTo(pokemon)));
    }

    @Test
    void testSaveMultipleCorrectInstances() {
        int numberOfPokemons = 5;
        List<Pokemon> pokemons = randomPokemons(numberOfPokemons);
        assertThat(pokemons.size(), is(equalTo(numberOfPokemons)));
        pokemonDao.save(pokemons);
        assertThat(pokemonDao.count(), is(equalTo((long) pokemons.size())));
        assertThat(pokemonDao.findAll(), is(equalTo(pokemons)));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSavePokemonWithNullName() {
        Pokemon pokemon = new Pokemon();

        pokemon.setTrainer(trainer);
        pokemon.setLevel(1);
        pokemon.setNickname("Killer");
        pokemon.setType(PokemonType.NORMAL);
        pokemon.setName(null);

        trainer.addPokemon(pokemon);

        pokemonDao.save(pokemon);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testSavePokemonWithNullType() {
        Pokemon pokemon = new Pokemon();

        pokemon.setTrainer(trainer);
        pokemon.setLevel(1);
        pokemon.setNickname("Killer");
        pokemon.setType(null);
        pokemon.setName("Pikachu");

        trainer.addPokemon(pokemon);

        pokemonDao.save(pokemon);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    void testSavePokemonWithInvalidLevel() {
        Pokemon pokemon = new Pokemon();

        pokemon.setTrainer(trainer);
        pokemon.setLevel(0);
        pokemon.setNickname("Killer");
        pokemon.setType(PokemonType.NORMAL);
        pokemon.setName("Pikachu");

        trainer.addPokemon(pokemon);

        pokemonDao.save(pokemon);
    }


    //Delete tests

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeleteNullPokemon() {
        Pokemon pokemon = null;
        assertThat(pokemon, is(nullValue(Pokemon.class)));
        pokemonDao.delete(pokemon);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    void testDeletePokemonWithNonExistingId() {
        pokemonDao.delete(99999L);
    }

    @Test
    void testDeleteByPassingInstance() {
        Pokemon pokemon = randomPokemon(9);
        assertThat(pokemonDao.count(), is(equalTo(0L)));
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        assertThat("pokemon.getId() == null", pokemon.getId(), is(notNullValue(Long.class)));
        pokemonDao.delete(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteByPassingId() {
        Pokemon pokemon = randomPokemon(10);
        assertThat(pokemonDao.count(), is(equalTo(0L)));
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        assertThat("pokemon.getId() == null", pokemon.getId(), is(notNullValue(Long.class)));
        pokemonDao.delete(pokemon.getId());
        assertThat(pokemonDao.count(), is(equalTo(0L)));
    }

    @Test
    void testDeleteMultipleInstances() {
        int numberOfPokemons = 6;
        List<Pokemon> pokemons = randomPokemons(numberOfPokemons);
        assertThat(pokemonDao.count(), is(equalTo(0L)));
        pokemonDao.save(pokemons);
        assertThat(pokemonDao.count(), is(equalTo((long) numberOfPokemons)));
        pokemonDao.delete(pokemons);
        assertThat(pokemonDao.count(), is(equalTo(0L)));
    }


    //Find tests

    @Test
    void testFindByPassingId() {
        Pokemon pokemon = randomPokemon(5);
        pokemonDao.save(pokemon);
        assertThat(pokemon.getId(), is(notNullValue()));
        Pokemon found = pokemonDao.findOne(pokemon.getId());
        assertThat(found, is(equalTo(pokemon)));
    }

    @Test
    void testFindByPassingExample() {
        Pokemon pokemon = randomPokemon(7);
        pokemonDao.save(pokemon);
        assertThat(pokemon.getId(), is(notNullValue()));
        Pokemon newPokemon = new Pokemon(pokemon.getType());
        newPokemon.setLevel(pokemon.getLevel());
        newPokemon.setName(pokemon.getName());
        newPokemon.setNickname(pokemon.getNickname());
        newPokemon.setTrainer(pokemon.getTrainer());
        Pokemon found = pokemonDao.findOne(Example.of(newPokemon));
        assertThat(found, is(equalTo(pokemon)));
    }

    @Test
    void testFindAll() {
        int numberOfPokemons = 10;
        assertThat(pokemonDao.findAll(), is(empty()));
        List<Pokemon> pokemons = randomPokemons(numberOfPokemons);
        pokemonDao.save(pokemons);
        List<Pokemon> found = pokemonDao.findAll();
        assertThat(found.size(), is(equalTo(numberOfPokemons)));
        assertThat(found, is(equalTo(pokemons)));
    }


    //Update tests

    @Test
    void testCorrectNameUpdate() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        pokemon.setName("Pikachu");
        pokemonDao.saveAndFlush(pokemon);
        Pokemon found = pokemonDao.findOne(pokemon.getId());
        assertThat(found, equalTo(pokemon));
    }

    @Test
    void testCorrectNickNameUpdate() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        pokemon.setNickname("Yellow mouse");
        pokemonDao.saveAndFlush(pokemon);
        Pokemon found = pokemonDao.findOne(pokemon.getId());
        assertThat(found, equalTo(pokemon));
    }

    @Test
    void testCorrectTypeUpdate() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        pokemon.setType(PokemonType.GHOST);
        pokemonDao.saveAndFlush(pokemon);
        Pokemon found = pokemonDao.findOne(pokemon.getId());
        assertThat(found, equalTo(pokemon));
    }

    @Test
    void testCorrectLevelUpdate() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        pokemon.setLevel(10);
        pokemonDao.saveAndFlush(pokemon);
        Pokemon found = pokemonDao.findOne(pokemon.getId());
        assertThat(found, equalTo(pokemon));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullName() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        pokemon.setName(null);
        pokemonDao.saveAndFlush(pokemon);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    void testUpdateWithNullType() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        pokemon.setType(null);
        pokemonDao.saveAndFlush(pokemon);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    void testUpdateWithInvalidLevel() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));
        pokemon.setLevel(0);
        pokemonDao.saveAndFlush(pokemon);
    }

    @Test
    void testCorrectTrainerUpdate() {
        Pokemon pokemon = randomPokemon(6);
        pokemonDao.save(pokemon);
        assertThat(pokemonDao.count(), is(equalTo(1L)));

        Pokemon found = pokemonDao.findOne(pokemon.getId());
        assertThat(found.getTrainer(), equalTo(trainer));

        Trainer newTrainer = new Trainer();
        Date dob = new Calendar.Builder().setDate(1995, 4, 13).build().getTime();
        newTrainer.setDateOfBirth(dob);
        newTrainer.setFirstName("Lance");
        newTrainer.setLastName("Dragons");
        newTrainer.addPokemon(pokemon);
        trainerDao.save(newTrainer);
        pokemon.setTrainer(newTrainer);

        pokemonDao.saveAndFlush(pokemon);
        found = pokemonDao.findOne(pokemon.getId());
        assertThat(found.getTrainer(), equalTo(newTrainer));
    }

    private void prepareTrainer() {
        trainer = new Trainer();
        trainer.setFirstName("Gary");
        trainer.setLastName("Oak");
        // 13.04.1993
        Date dob = new Calendar.Builder().setDate(1993, 4, 13).build().getTime();
        trainer.setDateOfBirth(dob);
    }

    private List<Pokemon> randomPokemons(int size) {
        List<Pokemon> pokemons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pokemons.add(randomPokemon(i));
        }
        assertThat(pokemons.size(), is(equalTo(size)));
        return pokemons;
    }

    private Pokemon randomPokemon(int index) {
        Pokemon pokemon = new Pokemon();
        String chars = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ ";
        pokemon.setName(chars.substring(index % chars.length()));
        pokemon.setNickname(chars.substring(index % chars.length()));
        pokemon.setType(PokemonType.FIRE);
        pokemon.setLevel(2);
        pokemon.setTrainer(trainer);

        trainer.addPokemon(pokemon);

        return pokemon;
    }
}

