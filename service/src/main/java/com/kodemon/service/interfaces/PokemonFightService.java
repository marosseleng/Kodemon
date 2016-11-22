package com.kodemon.service.interfaces;

import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.service.util.Pair;

/**
 * Service handling fight between two Pokemon
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface PokemonFightService {
    /**
     * Returns a {@link Pair} of fight scores for both, the challenger and the target Pokemon
     * Score is calculated as Pokemon's level multiplied by its fight advantage
     * <p/>
     * These scores are compared and counted into the result of the Trainer fight
     *
     * @param challenger challenger Pokemon
     * @param target target Pokemon
     * @return {@link Pair} of fight scores
     */
    Pair<Double, Double> getScorePair(Pokemon challenger, Pokemon target);
}
