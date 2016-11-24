package com.kodemon.service.test;

import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.PokemonFightAdvantageService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * @author Oliver Roch
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class PokemonFightAdvantageServiceTest extends AbstractTestNGSpringContextTests {
    @Inject
    private PokemonFightAdvantageService pokemonFightAdvantageService;

    private PokemonName challenger;
    private PokemonName target;

    @BeforeMethod
    public void initPokemonNames() {
        challenger = PokemonName.ARTICUNO;
        target = PokemonName.CHARIZARD;
    }

    @Test
    public void computePokemonFightAdvantageTest() {
        double pokemonFightAdvantage = pokemonFightAdvantageService.computePokemonFightAdvantage(challenger, target);
        Assert.assertEquals(1.0,pokemonFightAdvantage);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void computePokemonFightAdvantageWithNullNameTest() {
        double pokemonFightAdvantage = pokemonFightAdvantageService.computePokemonFightAdvantage(null, target);
    }
}