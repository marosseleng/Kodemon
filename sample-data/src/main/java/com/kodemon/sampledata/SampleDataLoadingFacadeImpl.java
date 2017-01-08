package com.kodemon.sampledata;

import com.kodemon.persistence.dao.TrainerFightDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.interfaces.GymService;
import com.kodemon.service.interfaces.PokemonService;
import com.kodemon.service.interfaces.TrainerService;
import com.kodemon.service.util.PasswordStorage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Calendar;

import static com.kodemon.persistence.util.Constants.INITIAL_POKEMON_LEVEL;

/**
 * @author Oliver Roch
 */

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    private GymService gymService;
    private TrainerService trainerService;
    private PokemonService pokemonService;
    private TrainerFightDao trainerFightDao;

    @Inject
    public SampleDataLoadingFacadeImpl(
            GymService gymService,
            TrainerService trainerService,
            PokemonService pokemonService,
            TrainerFightDao trainerFightDao) {
        this.gymService = gymService;
        this.trainerService = trainerService;
        this.pokemonService = pokemonService;
        this.trainerFightDao = trainerFightDao;
    }

    @Override
    public void loadData() throws PasswordStorage.CannotPerformOperationException {
        gymService.initializeGyms();

        Calendar cal = Calendar.getInstance();
        cal.set(1993, Calendar.MARCH, 24);

        Trainer ash = new Trainer();
        ash.setFirstName("Ash");
        ash.setLastName("Ketchum");
        ash.setUserName("Ash123");
        ash.setDateOfBirth(cal.getTime());
        ash.setPwdHash(PasswordStorage.createHash("password123"));
        ash = trainerService.save(ash);

        Pokemon pikachu = pokemonService.createPokemonWithName(PokemonName.PIKACHU);
        pikachu.setLevel(INITIAL_POKEMON_LEVEL);
        pokemonService.assignTrainerToPokemon(ash, pikachu);

        ash = trainerService.addPokemon(pikachu, ash);

        Trainer admin = new Trainer();
        admin.setUserName("admin");
        admin.setFirstName("Professor");
        admin.setLastName("Oak");
        cal.set(1963, Calendar.MARCH, 3);
        admin.setDateOfBirth(cal.getTime());
        admin.setAdmin(true);
        admin.setPwdHash(PasswordStorage.createHash("adminpassword"));

        Pokemon mewtwo = pokemonService.createPokemonWithName(PokemonName.MEWTWO);
        mewtwo.setLevel(999);
        pokemonService.assignTrainerToPokemon(admin, mewtwo);

        trainerService.addPokemon(mewtwo, admin);

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
        fight.setWasChallengerSuccessful(false);
        trainerFightDao.save(fight);
    }
}
