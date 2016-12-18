package com.kodemon.service.interfaces;

import com.kodemon.persistence.enums.PokemonName;

/**
 * This service computes the sum of coefficients describing what (dis)advantage a {@link PokemonName} has against other
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface PokemonFightAdvantageService {
    /**
     * Returns the sum of coefficients
     * that multipliy challenger Pokemon's level before the fight.
     * <p/>
     * Returned coefficient is greater than or equal to zero.
     * Zero means the Pokemon has no chance against the defender, whatever its level is.
     *
     * @param challenger challenger PokemonName
     * @param target     target PokemonName
     * @return coefficient to multiply challenger's level by
     */
    double computePokemonFightAdvantage(PokemonName challenger, PokemonName target);
}
