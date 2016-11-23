package com.kodemon.service.implementations;

import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.service.interfaces.PokemonFightAdvantageService;
import com.kodemon.service.interfaces.PokemonFightService;
import com.kodemon.service.util.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Oliver Roch
 */

@Service
public class PokemonFightServiceImpl implements PokemonFightService {
    @Inject
    private PokemonFightAdvantageService pokemonFightAdvantageService;

    @Override
    public Pair<Double, Double> getScorePair(Pokemon challenger, Pokemon target) {
        double advantageOfChallenger = pokemonFightAdvantageService.computePokemonFightAdvantage(challenger.getName(), target.getName());

        Double challengerScore = challenger.getLevel() * advantageOfChallenger;
        Double targetScore = (double) target.getLevel();

        return new Pair<>(challengerScore, targetScore);
    }
}
