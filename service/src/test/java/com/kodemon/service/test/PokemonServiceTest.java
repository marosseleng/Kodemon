package com.kodemon.service.test;

import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.PokemonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by mseleng on 11/25/16.
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class PokemonServiceTest extends AbstractTestNGSpringContextTests {

    private static final int NUMBER_OF_GENERATED_POKEMON = 30;

    @Mock
    private PokemonDao dao;

    @Inject
    @InjectMocks
    private PokemonService service;

    private Trainer trainer;
    private Pokemon pokemon;

    @BeforeClass
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveCorrectInstance() {
        prepareTrainer();
        preparePokemon();

        when(dao.save(pokemon)).thenReturn(pokemon);
        service.save(pokemon);
        verify(dao).save(pokemon);
    }

    @Test(expectedExceptions = NullPointerException.class)
    void testSaveNullInstance() {
        when(dao.save((Pokemon) null)).thenThrow(NullPointerException.class);
        service.save(null);
    }

    @Test
    void testDeleteExistingInstance() {
        prepareTrainer();
        preparePokemon();

        doNothing().when(dao).delete(pokemon);
        service.delete(pokemon);
        verify(dao).delete(pokemon);
    }

    @Test(expectedExceptions = DataAccessException.class)
    void testDeleteNotExistingInstance() {
        prepareTrainer();
        preparePokemon();

        doThrow(DataRetrievalFailureException.class).when(dao).delete(pokemon);
        service.delete(pokemon);
    }

    @Test(expectedExceptions = NullPointerException.class)
    void testDeleteNullInstance() {
        doThrow(NullPointerException.class).when(dao).delete((Pokemon) null);
        service.delete(null);
    }

    @Test
    void testLevelPokemonUp() {
        preparePokemon();

        // saving leveled up
        when(dao.save(pokemon)).thenReturn(pokemon);
        pokemon.setLevel(pokemon.getLevel() - 1);
        service.save(pokemon);
        pokemon.setLevel(pokemon.getLevel() + 1);
        verify(dao).save(pokemon);
    }

    @Test
    void testGenerateRandomPokemonWithNullType() {
        for (int i = 0; i < NUMBER_OF_GENERATED_POKEMON; i++) {
            Pokemon generated = service.generateWildPokemon(null, pokemon.getLevel() - 5, pokemon.getLevel() + 5);
            assertThat(generated, not(is(nullValue(Pokemon.class))));
            assertThat(generated.getTrainer(), is(nullValue(Trainer.class)));
            assertThat(generated.getName(), isIn(PokemonName.values()));
            assertThat(generated.getLevel(), is(greaterThanOrEqualTo(0)));
        }
    }

    @Test
    void testGenerateRandomPokemonWithNonNullType() {
        for (int i = 0; i < NUMBER_OF_GENERATED_POKEMON; i++) {
            PokemonType desiredType = PokemonType.values()[NUMBER_OF_GENERATED_POKEMON % PokemonType.values().length];
            Pokemon generated = service.generateWildPokemon(desiredType, pokemon.getLevel() - 5, pokemon.getLevel() + 5);
            // not(is(nullValue(Pokemon.class))) is a workaround, because isNotNull() is probably too general
            assertThat(generated, not(is(nullValue(Pokemon.class))));
            assertThat(desiredType, isIn(generated.getType()));
            assertThat(generated.getTrainer(), is(nullValue(Trainer.class)));
            assertThat(generated.getName(), isIn(PokemonName.values()));
            assertThat(generated.getLevel(), is(greaterThanOrEqualTo(0)));
        }
    }

    @Test
    void testAssignCorrectTrainerToCorrectPokemon() {
        prepareTrainer();

        Pokemon pokemon = new Pokemon(PokemonName.CHARMANDER);
        pokemon.setLevel(11);
        pokemon.setNickname("BBQ");
        pokemon.setTrainer(trainer);
        when(dao.save(pokemon)).thenReturn(pokemon);
        service.assignTrainerToPokemon(trainer, pokemon);
        verify(dao).save(pokemon);
    }

    @Test(expectedExceptions = DataAccessException.class)
    void testAssignTrainerThatIsNotInDb() {
        prepareTrainer();
        preparePokemon();

        when(dao.save(pokemon)).thenThrow(DataRetrievalFailureException.class);
        service.assignTrainerToPokemon(trainer, pokemon);
    }

    @Test(expectedExceptions = NullPointerException.class)
    void testAssignNullTrainer() {
        preparePokemon();

        pokemon.setTrainer(null);
        when(dao.save(pokemon)).thenThrow(NullPointerException.class);
        service.assignTrainerToPokemon(null, pokemon);
    }

    @Test(expectedExceptions = NullPointerException.class)
    void testAssignTrainerToNullPokemon() {
        prepareTrainer();

        service.assignTrainerToPokemon(trainer, null);
    }

    @Test
    void testFindByTrainer() {
        prepareTrainer();
        preparePokemon();

        when(dao.findByTrainer(trainer)).thenReturn(Collections.singletonList(pokemon));
        List<Pokemon> found = service.findByTrainer(trainer);
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(equalTo(pokemon)));
    }

    @Test
    void testFindByName() {
        prepareTrainer();
        PokemonName desiredName = PokemonName.CATERPIE;
        Pokemon pokemon = new Pokemon(desiredName);
        pokemon.setLevel(12);
        pokemon.setTrainer(trainer);
        pokemon.setNickname("Pie");

        when(dao.findByName(desiredName)).thenReturn(Collections.singletonList(pokemon));
        List<Pokemon> found = service.findByName(desiredName);
        assertThat(found.size(), is(equalTo(1)));
        assertThat(found.get(0), is(equalTo(pokemon)));
    }

    @Test
    void testFindByLevel() {
        preparePokemon();

        when(dao.findByLevel(pokemon.getLevel())).thenReturn(Collections.singletonList(pokemon));
        List<Pokemon> found = service.findByLevel(pokemon.getLevel());
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(equalTo(pokemon)));
    }

    @Test
    void testFindByNickname() {
        preparePokemon();

        when(dao.findByNickname(pokemon.getNickname())).thenReturn(Collections.singletonList(pokemon));
        List<Pokemon> found = service.findByNickname(pokemon.getNickname());
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(equalTo(pokemon)));
    }

    @Test
    void testFindByNicknameStartingWith() {
        preparePokemon();

        when(dao.findByNicknameStartingWith(pokemon.getNickname().substring(0, 2))).thenReturn(Collections.singletonList(pokemon));
        List<Pokemon> found = service.findByNicknameStartingWith(pokemon.getNickname().substring(0, 2));
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(equalTo(pokemon)));
    }

    @AfterMethod
    void resetMocks() {
        Mockito.reset(dao);
    }

    private void prepareTrainer() {
        trainer = new Trainer();
        trainer.setFirstName("Maros");
        trainer.setLastName("Seleng");
        trainer.setUserName("marosseleng");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1994, Calendar.FEBRUARY, 16);
        trainer.setDateOfBirth(calendar.getTime());
    }

    private void preparePokemon() {
        pokemon = new Pokemon(PokemonName.SHELLDER);
        pokemon.setLevel(13);
        pokemon.setTrainer(trainer);
        pokemon.setNickname("Shelly");
    }
}
