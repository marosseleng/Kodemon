package com.kodemon.service.test;

import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.PokemonFightAdvantageService;
import com.kodemon.service.interfaces.PokemonFightService;
import com.kodemon.service.util.Pair;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.when;

/**
 * @author Oliver Roch
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class PokemonFightServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private PokemonFightAdvantageService pokemonFightAdvantageService;

    @Inject
    @InjectMocks
    private PokemonFightService pokemonFightService;

    private Pokemon challenger;
    private Pokemon target;

    @BeforeMethod
    public void initPokemons() {
        challenger = new Pokemon();
        target = new Pokemon();

        challenger.setLevel(10);
        challenger.setName(PokemonName.ARTICUNO);
        challenger.setNickname("Arti");

        target.setLevel(6);
        target.setName(PokemonName.CHARIZARD);
        target.setNickname("Redflame");
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getScorePairTest() {
        when(pokemonFightAdvantageService.computePokemonFightAdvantage(PokemonName.ARTICUNO, PokemonName.CHARIZARD)).thenReturn(1.0);
        Pair<Double, Double> score = pokemonFightService.getScorePair(challenger, target);

        Assert.assertEquals(10.0, score.getX());
        Assert.assertEquals(6.0, score.getY());
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void getScorePairWithNullPokemonTest() {
        when(pokemonFightAdvantageService.computePokemonFightAdvantage(PokemonName.ARTICUNO, PokemonName.CHARIZARD)).thenReturn(1.0);
        Pair<Double, Double> score = pokemonFightService.getScorePair(null, target);
    }
}
