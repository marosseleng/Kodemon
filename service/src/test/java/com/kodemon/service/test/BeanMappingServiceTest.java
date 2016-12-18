package com.kodemon.service.test;

import com.kodemon.api.dto.*;
import com.kodemon.persistence.entity.*;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.BeanMappingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    Trainer challenger;
    Trainer defender;
    UserDTO challengerDTO;
    UserDTO defenderDTO;
    Pokemon challengerPokemon;
    PokemonDTO challengerPokemonDTO;
    Pokemon defenderPokemon;
    PokemonDTO defenderPokemonDTO;
    Gym gym;
    GymDTO gymDTO;
    TrainerFight trainerFight;
    FightDTO fightDTO;
    @Inject
    private BeanMappingService service;

    @BeforeMethod
    void setUp() {
        preparePokemons();
        prepareTrainers();
        challenger.addPokemon(challengerPokemon);
        challengerPokemon.setTrainer(challenger);
        challengerDTO.addPokemon(challengerPokemonDTO);
        challengerPokemonDTO.setTrainer(challengerDTO);
        defender.addPokemon(defenderPokemon);
        defenderPokemon.setTrainer(defender);
        defenderDTO.addPokemon(defenderPokemonDTO);
        defenderPokemonDTO.setTrainer(defenderDTO);
        prepareGymAndGymDTO();
        prepareFights();
    }

    @Test
    void testMapTrainerToUserDTO() {
        UserDTO got = service.mapTo(challenger, UserDTO.class);

        assertThat(got, is(equalTo(challengerDTO)));
    }

    @Test
    void testMapUserDTOToTrainer() {
        Trainer got = service.mapTo(challengerDTO, Trainer.class);

        assertThat(got, is(equalTo(challenger)));
    }

    @Test
    void testMapTrainerFightToFightDTO() {
        FightDTO got = service.mapTo(trainerFight, FightDTO.class);

        assertThat(got, is(equalTo(fightDTO)));
    }

    @Test
    void testMapFightDTOToTrainerFight() {
        TrainerFight got = service.mapTo(fightDTO, TrainerFight.class);

        assertThat(got, is(equalTo(trainerFight)));
    }

    @Test
    void testMapGymDtoToGym() {
        Gym got = service.mapTo(gymDTO, Gym.class);

        assertThat(got, is(equalTo(gym)));
    }

    @Test
    void testMapGymToGymDTO() {
        GymDTO got = service.mapTo(gym, GymDTO.class);

        assertThat(got, is(equalTo(gymDTO)));
    }

    @Test(enabled = false)
    void testMapBadgeToBadgeDTO() {
        prepareGymAndGymDTO();
        prepareTrainers();

        Badge badge = new Badge(gym);
        badge.setName("Some badge");

        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setName("Some badge");
        badgeDTO.setGym(gymDTO);

        BadgeDTO got = service.mapTo(badge, BadgeDTO.class);
        assertThat(got.getGym(), is(equalTo(badgeDTO.getGym())));
        assertThat(got.getName(), is(equalTo(badgeDTO.getName())));
    }

    @Test
    void testMapCollection() {
        List<Trainer> trainers = generateListOfMinimalTrainers();

        List<UserDTO> iterated = new ArrayList<>();
        for (Trainer trainer : trainers) {
            iterated.add(service.mapTo(trainer, UserDTO.class));
        }

        List<UserDTO> users = service.mapListTo(trainers, UserDTO.class);

        assertThat(users, is(equalTo(iterated)));
    }

    private void preparePokemons() {
        challengerPokemon = new Pokemon(PokemonName.ABRA);
        challengerPokemon.setLevel(12);
        challengerPokemon.setNickname("Little psycho");

        challengerPokemonDTO = new PokemonDTO();
        challengerPokemonDTO.setNickname("Little psycho");
        challengerPokemonDTO.setName(PokemonName.ABRA);
        challengerPokemonDTO.setLevel(12);

        defenderPokemon = new Pokemon(PokemonName.MRMIME);
        defenderPokemon.setLevel(8);
        defenderPokemon.setNickname("Ha-ha!");

        defenderPokemonDTO = new PokemonDTO();
        defenderPokemonDTO.setLevel(8);
        defenderPokemonDTO.setName(PokemonName.MRMIME);
        defenderPokemonDTO.setNickname("Ha-ha!");
    }

    private void prepareTrainers() {
        // I am not saving anything to db, just testing conversion,
        // so I do not need to implement bidirectional relationship here
        challenger = new Trainer();
        challenger.setFirstName("Maros");
        challenger.setLastName("Seleng");
        challenger.setUserName("MarosSeleng");
        Calendar challengerCal = Calendar.getInstance();
        challengerCal.set(1994, Calendar.JUNE, 6);
        challenger.setDateOfBirth(challengerCal.getTime());

        challengerDTO = new UserDTO();
        challengerDTO.setUserName("MarosSeleng");
        challengerDTO.setFirstName("Maros");
        challengerDTO.setLastName("Seleng");
        challengerDTO.setDateOfBirth(challengerCal.getTime());

        defender = new Trainer();
        Calendar defenderCal = Calendar.getInstance();
        defenderCal.set(1984, Calendar.JANUARY, 30);
        defender.setDateOfBirth(defenderCal.getTime());
        defender.setUserName("SomeDef001");
        defender.setLastName("Def");
        defender.setFirstName("Some");

        defenderDTO = new UserDTO();
        defenderDTO.setDateOfBirth(defenderCal.getTime());
        defenderDTO.setFirstName("Some");
        defenderDTO.setLastName("Def");
        defenderDTO.setUserName("SomeDef001");
    }

    private void prepareFights() {
        trainerFight = new TrainerFight();
        trainerFight.setChallenger(challenger);
        trainerFight.setTargetGym(gym);
        Calendar fightCal = Calendar.getInstance();
        fightCal.set(2016, Calendar.AUGUST, 17, 7, 24);
        trainerFight.setFightTime(fightCal.getTime());
        trainerFight.setWasChallengerSuccessful(false);

        fightDTO = new FightDTO();
        fightDTO.setFightTime(fightCal.getTime());
        fightDTO.setWasChallengerSuccessful(false);
        fightDTO.setChallenger(challengerDTO);
        fightDTO.setTargetGym(gymDTO);
    }

    private void prepareGymAndGymDTO() {
        gym = new Gym(defender);
        gym.setCity("Azure city");
        gym.setBadgeName("Azure Badge");
        gym.setType(PokemonType.WATER);

        gymDTO = new GymDTO();
        gymDTO.setType(PokemonType.WATER);
        gymDTO.setCity("Azure city");
        gymDTO.setBadgeName("Azure Badge");
        gymDTO.setTrainer(defenderDTO);
    }

    private List<Trainer> generateListOfMinimalTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(challenger);
        trainers.add(defender);
        return trainers;
    }
}
