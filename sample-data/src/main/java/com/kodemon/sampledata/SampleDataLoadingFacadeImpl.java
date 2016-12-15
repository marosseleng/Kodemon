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
import com.kodemon.service.util.PasswordStorage;
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
    private GymService gymService;
    private TrainerService trainerService;
    private TrainerDao trainerDao;
    private PokemonDao pokemonDao;
    private TrainerFightDao trainerFightDao;

    @Inject
    public SampleDataLoadingFacadeImpl(
            GymService gymService,
            TrainerService trainerService,
            TrainerDao trainerDao,
            PokemonDao pokemonDao,
            TrainerFightDao trainerFightDao) {
        this.gymService = gymService;
        this.trainerService = trainerService;
        this.trainerDao = trainerDao;
        this.pokemonDao = pokemonDao;
        this.trainerFightDao = trainerFightDao;
    }

    @Override
    public void loadData() throws PasswordStorage.CannotPerformOperationException {
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

        Pokemon mewtwo = new Pokemon(PokemonName.MEWTWO);
        mewtwo.setLevel(999);
        pokemonDao.save(mewtwo);

        Trainer admin = new Trainer();
        admin.setUserName("admin");
        admin.setFirstName("Professor");
        admin.setLastName("Oak");
        Date dob2 = new Calendar.Builder().setDate(1963, 24, 3).build().getTime();
        admin.setDateOfBirth(dob2);
        admin.setAdmin(true);
        admin.addPokemon(mewtwo);

        trainerService.register(admin, "adminpassword");

        mewtwo.setTrainer(admin);
        pokemonDao.save(mewtwo);


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
