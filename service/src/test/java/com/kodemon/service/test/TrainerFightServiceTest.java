package com.kodemon.service.test;

import com.kodemon.persistence.dao.TrainerFightDao;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.PokemonFightService;
import com.kodemon.service.interfaces.TrainerFightService;
import com.kodemon.service.util.Pair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author Oliver Roch
 */

@ContextConfiguration(classes=ServiceConfig.class)
public class TrainerFightServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    private PokemonFightService pokemonFightService;

    @Mock
    private TrainerFightDao trainerFightDao;

    @Inject
    @InjectMocks
    private TrainerFightService trainerFightService;

    private Trainer challenger;
    private Trainer defender;

    private TrainerFight firstFight;
    private TrainerFight secondFight;

    private Pokemon pikachu;
    private Pokemon onix;

    private Gym targetGym1;
    private Gym targetGym2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepare() {
        pikachu = new Pokemon();
        pikachu.setName(PokemonName.PIKACHU);
        pikachu.setLevel(4);
        pikachu.setTrainer(challenger);
        pikachu.setNickname("Yellow mouse");

        challenger = new Trainer();
        challenger.setFirstName("Ash");
        challenger.setLastName("Ketchum");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        challenger.setDateOfBirth(dob);
        challenger.setUserName("Ash123");
        challenger.addPokemon(pikachu);

        onix = new Pokemon();
        onix.setName(PokemonName.ONIX);
        onix.setLevel(5);
        onix.setNickname("The Rock");
        onix.setTrainer(defender);

        defender = new Trainer();
        defender.setFirstName("Brock");
        defender.setLastName("Takechi");
        Date dob2 = new Calendar.Builder().setDate(1984, 2, 3).build().getTime();
        defender.setDateOfBirth(dob2);
        defender.setUserName("Brocky123");
        defender.addPokemon(onix);

        targetGym1 = new Gym();
        targetGym1.setTrainer(defender);
        targetGym1.setCity("Violet city");
        targetGym1.setType(PokemonType.GROUND);

        targetGym2 = new Gym();
        targetGym2.setTrainer(challenger);
        targetGym2.setCity("Saffron city");
        targetGym2.setType(PokemonType.ELECTRIC);

        firstFight = new TrainerFight();
        firstFight.setChallenger(challenger);
        firstFight.setTargetGym(targetGym1);
        Date dof1 = new Calendar.Builder().setDate(2011, 4, 1).build().getTime();
        firstFight.setFightTime(dof1);
        firstFight.setWasChallengerSuccessful(false);

        secondFight = new TrainerFight();
        secondFight.setChallenger(defender);
        secondFight.setTargetGym(targetGym2);
        Date dof2 = new Calendar.Builder().setDate(2011, 5, 1).build().getTime();
        secondFight.setFightTime(dof2);
        secondFight.setWasChallengerSuccessful(true);
    }

    @Test
    public void wasFightForBadgeSuccessfulTest() {
        when(pokemonFightService.getScorePair(pikachu, onix)).thenReturn(new Pair<Double, Double>(3.2, 5.0));
        Boolean result = trainerFightService.wasFightForBadgeSuccessful(challenger, defender);
        assertThat(result, is(false));
    }

    @Test
    public void wasFightForBadgeSuccessfulWithMorePokemonsTest() {
        Pokemon articuno = new Pokemon();
        articuno.setLevel(10);
        articuno.setName(PokemonName.ARTICUNO);
        articuno.setNickname("Arti");
        articuno.setTrainer(challenger);
        challenger.addPokemon(articuno);

        Pokemon charizard = new Pokemon();
        charizard.setLevel(6);
        charizard.setName(PokemonName.CHARIZARD);
        charizard.setNickname("Redflame");
        charizard.setTrainer(defender);
        defender.addPokemon(charizard);

        when(pokemonFightService.getScorePair(pikachu, onix)).thenReturn(new Pair<Double, Double>(3.2, 5.0));
        when(pokemonFightService.getScorePair(articuno, charizard)).thenReturn(new Pair<Double, Double>(10.0, 6.0));
        Boolean result = trainerFightService.wasFightForBadgeSuccessful(challenger, defender);
        assertThat(result, is(true));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void wasFightForBadgeSuccessfulWithNullChallengerTest() {
        Boolean result = trainerFightService.wasFightForBadgeSuccessful(null, defender);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void wasFightForBadgeSuccessfulWithNullDefenderTest() {
        Boolean result = trainerFightService.wasFightForBadgeSuccessful(challenger, null);
    }

    @Test
    public void findByChallengerTest() {
        when(trainerFightDao.findByChallenger(challenger)).thenReturn(Collections.singletonList(firstFight));

        List<TrainerFight> result = trainerFightService.findByChallenger(challenger);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(firstFight));

        verify(trainerFightDao).findByChallenger(challenger);
    }

    @Test
    public void findByTargetGymTest() {
        when(trainerFightDao.findByTargetGym(targetGym2)).thenReturn(Collections.singletonList(secondFight));

        List<TrainerFight> result = trainerFightService.findByTargetGym(targetGym2);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(secondFight));

        verify(trainerFightDao).findByTargetGym(targetGym2);
    }

    @Test
    public void findByFightTimeBetweenTest() {
        Date dof1 = new Calendar.Builder().setDate(2011, 3, 30).build().getTime();
        Date dof2 = new Calendar.Builder().setDate(2011, 4, 3).build().getTime();
        when(trainerFightDao.findByFightTimeBetween(dof1, dof2)).thenReturn(Collections.singletonList(firstFight));

        List<TrainerFight> result = trainerFightService.findByFightTimeBetween(dof1, dof2);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(firstFight));

        verify(trainerFightDao).findByFightTimeBetween(dof1, dof2);
    }

    @Test
    public void findAllTest() {
        List<TrainerFight> toBeReturned = new ArrayList<>();
        toBeReturned.add(firstFight);
        toBeReturned.add(secondFight);
        when(trainerFightDao.findAll()).thenReturn(toBeReturned);

        List<TrainerFight> result = trainerFightService.findAll();
        assertThat(result.size(), is(2));
        assertThat(result, is(toBeReturned));

        verify(trainerFightDao).findAll();
    }

    @Test
    public void successfulSaveTest() {
        when(trainerFightDao.save(firstFight)).thenReturn(firstFight);
        trainerFightService.save(firstFight);
        verify(trainerFightDao).save(firstFight);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveNullObjectTest() {
        firstFight = null;
        when(trainerFightDao.save(firstFight)).thenThrow(NullPointerException.class);
        trainerFightService.save(firstFight);
        verify(trainerFightDao).save(firstFight);
    }
}
