package com.kodemon.service.test;

import com.kodemon.api.dto.FightDTO;
import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.BeanMappingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    private BeanMappingService service;

    Trainer trainer;
    UserDTO userDTO;

    Gym gym;
    GymDTO gymDTO;

    TrainerFight trainerFight;
    FightDTO fightDTO;

    @Test
    void testMapTrainerToUserDTO() {
        prepareTrainerAndUserDTO();

        UserDTO got = service.mapTo(trainer, UserDTO.class);
        assertThat(got.getFirstName(), is(equalTo(trainer.getFirstName())));
        assertThat(got.getLastName(), is(equalTo(trainer.getLastName())));
        assertThat(got.getUserName(), is(equalTo(trainer.getUserName())));
        assertThat(got.getBadges(), is(equalTo(trainer.getBadges())));
        assertThat(got.getDateOfBirth(), is(equalTo(trainer.getDateOfBirth())));
        assertThat(got.getPokemons(), is(equalTo(userDTO.getPokemons())));
    }

    @Test
    void testMapUserDTOToTrainer() {
        prepareTrainerAndUserDTO();

        Trainer got = service.mapTo(userDTO, Trainer.class);
        assertThat(got.getFirstName(), is(equalTo(userDTO.getFirstName())));
        assertThat(got.getLastName(), is(equalTo(userDTO.getLastName())));
        assertThat(got.getUserName(), is(equalTo(userDTO.getUserName())));
        assertThat(got.getBadges(), is(equalTo(userDTO.getBadges())));
        assertThat(got.getDateOfBirth(), is(equalTo(userDTO.getDateOfBirth())));
        assertThat(got.getPokemons(), is(equalTo(trainer.getPokemons())));
    }

    @Test
    void testMapTrainerFightToFightDTO() {
        prepareTrainerFightAndFightDTO();

        FightDTO got = service.mapTo(trainerFight, FightDTO.class);
        assertThat(got.getChallenger(), is(equalTo(service.mapTo(trainerFight.getChallenger(), UserDTO.class))));
        assertThat(got.getTargetGym(), is(equalTo(service.mapTo(trainerFight.getTargetGym(), GymDTO.class))));
        assertThat(got.getFightTime(), is(equalTo(trainerFight.getFightTime())));
    }

    @Test
    void testMapFightDTOToTrainerFight() {
        prepareTrainerFightAndFightDTO();

        TrainerFight got = service.mapTo(fightDTO, TrainerFight.class);

        assertThat(got.getChallenger(), is(equalTo(service.mapTo(fightDTO.getChallenger(), Trainer.class))));
        assertThat(got.getTargetGym(), is(equalTo(service.mapTo(fightDTO.getTargetGym(), Gym.class))));
        assertThat(got.getFightTime(), is(equalTo(trainerFight.getFightTime())));
    }

    @Test
    void testMapGymDtoToGym() {
        prepareGymAndGymDTO();

        Gym got = service.mapTo(gymDTO, Gym.class);
        assertThat(got.getTrainer(), is(equalTo(service.mapTo(gymDTO.getTrainer(), Trainer.class))));
        assertThat(got.getCity(), is(equalTo(gymDTO.getCity())));
        assertThat(got.getType(), is(equalTo(gymDTO.getType())));
    }

    @Test
    void testMapGymToGymDTO() {
        prepareGymAndGymDTO();

        GymDTO got = service.mapTo(gym, GymDTO.class);
        assertThat(got.getTrainer(), is(equalTo(service.mapTo(gym.getTrainer(), UserDTO.class))));
        assertThat(got.getCity(), is(equalTo(gym.getCity())));
        assertThat(got.getType(), is(equalTo(gym.getType())));
    }

    @Test
    void testMapCollection() {
        Set<Trainer> trainers = generateSetOfMinimalTrainers();

        List<UserDTO> iterated = new ArrayList<>();
        for (Trainer trainer : trainers) {
            iterated.add(service.mapTo(trainer, UserDTO.class));
        }

        List<UserDTO> users = service.mapTo(trainers, UserDTO.class);

        assertThat(users, is(equalTo(iterated)));
    }

    private void prepareTrainerAndUserDTO() {
        Pokemon pokemon = new Pokemon(PokemonName.ABRA);
        // I am not saving anything to db, just testing conversion,
        // so I do not need to implement bidirectional relationship here
        trainer = new Trainer(pokemon);
        trainer.setFirstName("Maros");
        trainer.setLastName("Seleng");
        trainer.setUserName("MarosSeleng");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1994, Calendar.JUNE, 6);
        trainer.setDateOfBirth(calendar.getTime());

        userDTO = new UserDTO();
        userDTO.setUserName("MarosSeleng");
        userDTO.setFirstName("Maros");
        userDTO.setLastName("Seleng");
        userDTO.setDateOfBirth(calendar.getTime());
        userDTO.addPokemon(service.mapTo(pokemon, PokemonDTO.class));
    }

    private void prepareTrainerFightAndFightDTO() {
        Pokemon p1 = new Pokemon(PokemonName.ABRA);
        Trainer t1 = new Trainer(p1);
        t1.setFirstName("Maros");
        t1.setLastName("Seleng");
        t1.setUserName("MarosSeleng");
        Calendar c1 = Calendar.getInstance();
        c1.set(1994, Calendar.JUNE, 6);
        t1.setDateOfBirth(c1.getTime());

        Pokemon p2 = new Pokemon(PokemonName.DODRIO);
        Trainer t2 = new Trainer(p2);
        t2.setFirstName("Soram");
        t2.setLastName("Gneles");
        t2.setUserName("GnelesSoram");
        Calendar c2 = Calendar.getInstance();
        c2.set(1997, Calendar.AUGUST, 22);
        t2.setDateOfBirth(c2.getTime());

        Gym gym = new Gym(t2);
        gym.setType(PokemonType.FLYING);
        gym.setCity("City above skies");

        trainerFight = new TrainerFight();
        trainerFight.setChallenger(t1);
        trainerFight.setTargetGym(gym);
        Calendar fightCal = Calendar.getInstance();
        fightCal.set(2016, Calendar.AUGUST, 17, 7, 24);
        trainerFight.setFightTime(fightCal.getTime());
        trainerFight.setWasChallengerSuccessful(false);

        UserDTO userDTOChallenger = new UserDTO();
        userDTOChallenger.setUserName("MarosSeleng");
        userDTOChallenger.setFirstName("Maros");
        userDTOChallenger.setLastName("Seleng");
        userDTOChallenger.setDateOfBirth(c1.getTime());

        UserDTO userDTODefender = new UserDTO();
        userDTODefender.setFirstName("Soram");
        userDTODefender.setLastName("Gneles");
        userDTODefender.setUserName("GnelesSoram");
        userDTODefender.setDateOfBirth(c2.getTime());

        GymDTO gymDTO = new GymDTO();
        gymDTO.setType(PokemonType.FLYING);
        gymDTO.setCity("City above skies");
        gymDTO.setTrainer(userDTODefender);

        fightDTO = new FightDTO();
        fightDTO.setFightTime(fightCal.getTime());
        fightDTO.setWasChallengerSuccessful(false);
        fightDTO.setChallenger(userDTOChallenger);
        fightDTO.setTargetGym(gymDTO);
    }

    private void prepareGymAndGymDTO() {
        Pokemon pokemon = new Pokemon(PokemonName.ABRA);
        // I am not saving anything to db, just testing conversion,
        // so I do not need to implement bidirectional relationship here
        Trainer trainer = new Trainer(pokemon);
        trainer.setFirstName("Maros");
        trainer.setLastName("Seleng");
        trainer.setUserName("MarosSeleng");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1994, Calendar.JUNE, 6);
        trainer.setDateOfBirth(calendar.getTime());

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("MarosSeleng");
        userDTO.setFirstName("Maros");
        userDTO.setLastName("Seleng");
        userDTO.setDateOfBirth(calendar.getTime());

        gym = new Gym(trainer);
        gym.setCity("Azure city");
        gym.setType(PokemonType.WATER);

        gymDTO = new GymDTO();
        gymDTO.setType(PokemonType.WATER);
        gymDTO.setCity("Azure city");
        gymDTO.setTrainer(userDTO);
    }

    private Set<Trainer> generateSetOfMinimalTrainers() {
        Set<Trainer> trainers = new HashSet<>();
        Trainer t1 = new Trainer(new Pokemon(PokemonName.CLEFAIRY));
        t1.setFirstName("John");
        t1.setLastName("Cena");
        t1.setUserName("YouCantSeeMe08");
        t1.setDateOfBirth(new Date());
        trainers.add(t1);
        Trainer t2 = new Trainer(new Pokemon(PokemonName.CHANSEY));
        t2.setFirstName("Hulk");
        t2.setLastName("Hogan");
        t2.setUserName("roaaaaargh345");
        t2.setDateOfBirth(new Date());
        trainers.add(t2);
        Trainer t3 = new Trainer(new Pokemon(PokemonName.CLOYSTER));
        t3.setFirstName("The");
        t3.setLastName("Rock");
        t3.setUserName("alwaysHard77");
        t3.setDateOfBirth(new Date());
        trainers.add(t3);
        Trainer t4 = new Trainer(new Pokemon(PokemonName.VILEPLUME));
        t4.setFirstName("The");
        t4.setLastName("Kane");
        t4.setUserName("burntDown");
        t4.setDateOfBirth(new Date());
        trainers.add(t4);
        return trainers;
    }
}
