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
 * @author Oliver Roch
 */

@Service
public class TrainerFightServiceImpl implements TrainerFightService {
    @Inject
    private TrainerFightDao trainerFightDao;

    @Override
    public boolean wasFightForBadgeSuccessful(Trainer challenger, Trainer defender) {
        PokemonFightService pokemonFightService = new PokemonFightServiceImpl();

        double challengerScore = 0;
        double defenderScore = 0;

        for(int i = 0; i < 6; i++) {
            Pokemon currentChallengerPokemon = challenger.getPokemons().get(i);
            Pokemon currentDefenderPokemon = defender.getPokemons().get(i);
            Pair<Double, Double> scorePair = pokemonFightService.getScorePair(currentChallengerPokemon, currentDefenderPokemon);

            challengerScore += scorePair.getX();
            defenderScore += scorePair.getY();
        }

        return challengerScore > defenderScore;
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
