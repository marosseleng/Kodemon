package com.kodemon.service;

import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.service.util.Pair;

/**
 * Created by mseleng on 11/21/16.
 */
public interface PokemonFightService {
    /**
     * Returns a {@link Pair} of fight scores for both, the challenger and the target Pokemon
     * Score is calculated as Pokemon's level multiplied by its fight advantage
     *
     * @param challenger challenger Pokemon
     * @param target target Pokemon
     * @return
     */
    Pair<Double, Double> getScorePair(Pokemon challenger, Pokemon target);
}
