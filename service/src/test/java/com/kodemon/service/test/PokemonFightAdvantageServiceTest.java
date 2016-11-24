package com.kodemon.service.test;

import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.PokemonFightAdvantageService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * Created by Oliver on 24.11.2016.
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class PokemonFightAdvantageServiceTest extends AbstractTestNGSpringContextTests {
    @Inject
    @InjectMocks
    private PokemonFightAdvantageService pokemonFightAdvantageService;

    private PokemonName challenger;
    private PokemonName target;

    @BeforeMethod
    public void initPokemonNames() {
        challenger = PokemonName.ARTICUNO;
        target = PokemonName.CHARIZARD;
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void computePokemonFightAdvantageTest() {
        double pokemonFightAdvantage = pokemonFightAdvantageService.computePokemonFightAdvantage(challenger, target);
        Assert.assertEquals(1,pokemonFightAdvantage);
    }
}
