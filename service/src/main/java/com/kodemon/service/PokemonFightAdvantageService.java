package com.kodemon.service;

import com.kodemon.persistence.enums.PokemonName;

/**
 * Created by mseleng on 11/21/16.
 */
public interface PokemonFightAdvantageService {
    /**
     * Returns the sum of coefficients
     * that multipliy challenger Pokemon's level before the fight.
     * </p>
     * Returned coefficient is greater than or equal to zero.
     * Zero means the Pokemon has no chance against the defender, whatever its level is.
     *
     * @param challenger challenger PokemonName
     * @param target target PokemonName
     * @return coefficient to multiply challenger's level by
     */
    double computePokemonFightAdvantage(PokemonName challenger, PokemonName target);
}
