package com.kodemon.service.test;

import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.PokemonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Calendar;

import static org.mockito.Mockito.*;

/**
 * Created by mseleng on 11/25/16.
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class PokemonServiceTest extends AbstractTestNGSpringContextTests {

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
    void testGenerateRandomPokemon() {

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

/*
Pokemon generateWildPokemon(@Nullable PokemonType type);
void levelPokemonUp(Pokemon pokemon);
void assignTrainerToPokemon(Trainer trainer, Pokemon pokemon);
void save(Pokemon pokemon);
void delete(Pokemon pokemon);
List<Pokemon> findByTrainer(Trainer trainer);
List<Pokemon> findByName(PokemonName name);
List<Pokemon> findByNicknameStartingWith(String prefix);
List<Pokemon> findByNickname(String nickname);
List<Pokemon> findByLevel(int level);
 */
