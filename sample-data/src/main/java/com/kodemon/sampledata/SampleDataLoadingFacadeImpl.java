package com.kodemon.sampledata;

import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
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

    @Inject
    public SampleDataLoadingFacadeImpl(
            GymService gymService,
            TrainerService trainerService,
            TrainerDao trainerDao,
            PokemonDao pokemonDao) {
        this.gymService = gymService;
        this.trainerService = trainerService;
        this.trainerDao = trainerDao;
        this.pokemonDao = pokemonDao;

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
    }
}
