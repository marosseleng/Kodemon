package com.kodemon.sampledata;

import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
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

    }
}
