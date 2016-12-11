package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.TrainerFightDao;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import com.kodemon.service.interfaces.PokemonFightService;
import com.kodemon.service.interfaces.TrainerFightService;
import com.kodemon.service.util.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Service for TrainerFights. It can find you fights played at specified time range and simulate fight,
 * determine, whether match for a badge was successful and save the result.
 *
 * @author Oliver Roch
 */

@Service
public class TrainerFightServiceImpl implements TrainerFightService {

    public static final int AMOUNT_OF_POKEMONS_FOR_MATCH = 6;

    private TrainerFightDao trainerFightDao;
    private PokemonFightService pokemonFightService;

    @Inject
    public TrainerFightServiceImpl(TrainerFightDao trainerFightDao, PokemonFightService pokemonFightService) {
        this.trainerFightDao = trainerFightDao;
        this.pokemonFightService = pokemonFightService;
    }

    @Override
    public boolean wasFightForBadgeSuccessful(Trainer challenger, Trainer defender) {
        double challengerScore = 0;
        double defenderScore = 0;

        for(int i = 0; i < AMOUNT_OF_POKEMONS_FOR_MATCH; i++) {
            Pair<Double, Double> scorePair;
            if(challenger.getPokemons().size() > i && defender.getPokemons().size() > i) {
                Pokemon currentChallengerPokemon = challenger.getPokemons().get(i);
                Pokemon currentDefenderPokemon = defender.getPokemons().get(i);
                scorePair = pokemonFightService.getScorePair(currentChallengerPokemon, currentDefenderPokemon);
            } else if(challenger.getPokemons().size() <= i && defender.getPokemons().size() > i) {
                scorePair = new Pair<>(0.0, (double) defender.getPokemons().get(i).getLevel());
            } else if(challenger.getPokemons().size() > i && defender.getPokemons().size() <= i) {
                scorePair = new Pair<>((double) challenger.getPokemons().get(i).getLevel(), 0.0);
            } else {
                scorePair = new Pair<>(0.0, 0.0);
            }

            challengerScore += scorePair.getX();
            defenderScore += scorePair.getY();
        }

        return challengerScore > defenderScore;
    }

    @Override
    public TrainerFight findById(Long id) {
        return trainerFightDao.findOne(id);
    }

    @Override
    public List<TrainerFight> findByChallenger(Trainer challenger) {
        return trainerFightDao.findByChallenger(challenger);
    }

    @Override
    public List<TrainerFight> findByTargetGym(Gym targetGym) {
        return trainerFightDao.findByTargetGym(targetGym);
    }

    @Override
    public List<TrainerFight> findByFightTimeBetween(Date from, Date til) {
        return trainerFightDao.findByFightTimeBetween(from, til);
    }

    @Override
    public List<TrainerFight> findAll() {
        return trainerFightDao.findAll();
    }

    @Override
    public void save(TrainerFight trainerFight) {
        trainerFightDao.save(trainerFight);
    }
}
