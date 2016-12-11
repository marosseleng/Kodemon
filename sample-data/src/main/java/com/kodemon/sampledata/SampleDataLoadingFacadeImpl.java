package com.kodemon.sampledata;

import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.dao.TrainerFightDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.interfaces.GymService;
import com.kodemon.service.interfaces.TrainerService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Oliver Roch
 */

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    @Inject
    private GymService gymService;

    @Inject
    private TrainerService trainerService;

    @Inject
    private TrainerDao trainerDao;

    @Inject
    private TrainerFightDao trainerFightDao;

    @Inject
    private PokemonDao pokemonDao;

    @Override
    public void loadData() {
        gymService.initializeGyms();

        Pokemon pikachu = new Pokemon(PokemonName.PIKACHU);
        pikachu.setLevel(1);
        pokemonDao.save(pikachu);

        Trainer ash = new Trainer();
        ash.setUserName("Ash123");
        ash.setFirstName("Ash");
        ash.setLastName("Ketchum");
        Date dob = new Calendar.Builder().setDate(1993, 24, 3).build().getTime();
        ash.setDateOfBirth(dob);
        ash.addPokemon(pikachu);

        trainerService.register(ash, "password123");

        pikachu.setTrainer(ash);
        pokemonDao.save(pikachu);



        Pokemon pikachu2 = new Pokemon(PokemonName.PIKACHU);
        pikachu2.setLevel(3);
        pokemonDao.save(pikachu2);

        Trainer ash2 = new Trainer();
        ash2.setUserName("Ash2123");
        ash2.setFirstName("Ash2");
        ash2.setLastName("Ketchum2");
        Date dob2 = new Calendar.Builder().setDate(1993, 24, 3).build().getTime();
        ash2.setDateOfBirth(dob2);
        ash2.addPokemon(pikachu2);

        trainerService.register(ash2, "password2123");

        pikachu2.setTrainer(ash2);
        pokemonDao.save(pikachu2);




        TrainerFight fight;

        fight = new TrainerFight();
        fight.setChallenger(ash);
        fight.setTargetGym(gymService.findAll().get(0));
        fight.setFightTime(new Calendar.Builder().setDate(2016, 10, 23).build().getTime());
        fight.setWasChallengerSuccessful(false);
        trainerFightDao.save(fight);

        fight = new TrainerFight();
        fight.setChallenger(ash);
        fight.setTargetGym(gymService.findAll().get(0));
        fight.setFightTime(new Calendar.Builder().setDate(2016, 11, 2).build().getTime());
        fight.setWasChallengerSuccessful(false);
        trainerFightDao.save(fight);

        fight = new TrainerFight();
        fight.setChallenger(ash);
        fight.setTargetGym(gymService.findAll().get(0));
        fight.setFightTime(new Calendar.Builder().setDate(2016, 11, 11).build().getTime());
        fight.setWasChallengerSuccessful(true);
        trainerFightDao.save(fight);
    }
}
