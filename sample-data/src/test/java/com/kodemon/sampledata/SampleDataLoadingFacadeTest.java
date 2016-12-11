package com.kodemon.sampledata;

import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.dao.TrainerDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * @author Oliver Roch
 */

@ContextConfiguration(classes = {SampleDataConfig.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoadingFacadeTest extends AbstractTestNGSpringContextTests {

    @Inject
    private GymDao gymDao;

    @Inject
    private PokemonDao pokemonDao;

    @Inject
    private TrainerDao trainerDao;

    @Inject
    private SampleDataLoadingFacade sampleDataLoadingFacade;

    @Test
    public void createSampleDataTest() {
        Assert.assertEquals(gymDao.findAll().size(), 8);
        Assert.assertEquals(trainerDao.findAll().size(), 9);
        Assert.assertEquals(pokemonDao.findAll().size(), 45);
    }
}
