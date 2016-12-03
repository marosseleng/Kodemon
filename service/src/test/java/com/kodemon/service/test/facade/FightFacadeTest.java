package com.kodemon.service.test.facade;

import com.kodemon.api.dto.*;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.dao.TrainerFightDao;
import com.kodemon.persistence.entity.*;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.facade.FightFacadeImpl;
import com.kodemon.service.implementations.BeanMappingServiceImpl;
import com.kodemon.service.interfaces.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Oliver Roch
 */

@ContextConfiguration(classes=ServiceConfig.class)
public class FightFacadeTest extends AbstractTestNGSpringContextTests {
    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private TrainerFightService trainerFightService;

    @Inject
    private PokemonFightService pokemonFightService;

    @Inject
    private PokemonService pokemonService;

    @Inject
    private TrainerService trainerService;

    @Inject
    private BadgeService badgeService;

    @Inject
    private TimeService timeService;

    @Inject
    private FightFacade fightFacade;

    @Inject
    private TrainerDao trainerDao;

    @Inject
    private PokemonDao pokemonDao;

    @Inject
    private GymDao gymDao;

    @Inject
    private TrainerFightDao trainerFightDao;

    private UserDTO challenger;
    private UserDTO defender;
    private GymDTO targetGym;
    private PokemonDTO pikachu;
    private PokemonDTO onix;
    private TrainerFight fight1e;
    private TrainerFight fight2e;

    @BeforeMethod
    public void prepare() {
        prepareTrainers();
        prepareGym();
        prepareFights();
    }

    @Test
    public void fightForBadgeTest() {
        Set<BadgeDTO> badgeSet = challenger.getBadges();
        List<TrainerFight> fights = trainerFightService.findAll();
        fightFacade.fightForBadge(challenger, targetGym);
    }

    @Test
    public void fightWildPokemonTest() {

    }

    @Test
    public void listFightsBetweenTest() {
        Date from = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        Date to = new Calendar.Builder().setDate(1984, 2, 3).build().getTime();
        List<FightDTO> fights = fightFacade.listFightsBetween(from, to);
        assertThat(fights.size(), is(2));
    }


    private void prepareTrainers() {
        pikachu = new PokemonDTO();
        pikachu.setName(PokemonName.PIKACHU);
        pikachu.setLevel(20);
        pikachu.setNickname("Yellow mouse");

        pokemonDao.save(beanMappingService.mapTo(pikachu, Pokemon.class));

        challenger = new UserDTO();
        challenger.setFirstName("Ash");
        challenger.setLastName("Ketchum");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        challenger.setDateOfBirth(dob);
        challenger.setUserName("Ash123");
        challenger.addPokemon(pikachu);

        trainerDao.save(beanMappingService.mapTo(challenger, Trainer.class));

        pikachu.setTrainer(challenger);

        onix = new PokemonDTO();
        onix.setName(PokemonName.ONIX);
        onix.setLevel(5);
        onix.setNickname("The Rock");

        pokemonDao.save(beanMappingService.mapTo(onix, Pokemon.class));

        defender = new UserDTO();
        defender.setFirstName("Brock");
        defender.setLastName("Takechi");
        Date dob2 = new Calendar.Builder().setDate(1984, 2, 3).build().getTime();
        defender.setDateOfBirth(dob2);
        defender.setUserName("Brocky123");
        defender.addPokemon(onix);

        trainerDao.save(beanMappingService.mapTo(defender, Trainer.class));

        onix.setTrainer(defender);
    }

    private void prepareGym() {
        targetGym = new GymDTO();
        targetGym.setTrainer(defender);
        targetGym.setCity("Violet city");
        targetGym.setType(PokemonType.GROUND);

        gymDao.save(beanMappingService.mapTo(targetGym, Gym.class));
    }

    private void prepareFights() {
        fight1e = new TrainerFight();
        fight1e.setWasChallengerSuccessful(false);
        Date dof1e = new Calendar.Builder().setDate(2015, 4, 1).build().getTime();
        fight1e.setFightTime(dof1e);
        fight1e.setChallenger(beanMappingService.mapTo(challenger, Trainer.class));
        fight1e.setTargetGym(beanMappingService.mapTo(targetGym, Gym.class));

        trainerFightDao.save(fight1e);

        fight2e = new TrainerFight();
        fight2e.setWasChallengerSuccessful(false);
        Date dof2e = new Calendar.Builder().setDate(2015, 4, 3).build().getTime();
        fight2e.setFightTime(dof2e);
        fight2e.setChallenger(beanMappingService.mapTo(challenger, Trainer.class));
        fight2e.setTargetGym(beanMappingService.mapTo(targetGym, Gym.class));

        trainerFightDao.save(fight2e);
    }
}
