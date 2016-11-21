package com.kodemon.service;

import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.enums.PokemonType;
import com.sun.istack.internal.Nullable;

/**
 * Created by mseleng on 11/19/16.
 */
public interface PokemonService {
    /**
     * Generates random wild Pokemon of the given type.
     * A wild {@link com.kodemon.persistence.entity.Pokemon} has no {@link com.kodemon.persistence.entity.Trainer}.
     * </p>
     * When the {@param type} is {@code null}, Pokemon type is random also.
     *
     * @param type (optional) type of the Pokemon
     * @return generated wild Pokemon
     */
    Pokemon generateWildPokemon(@Nullable PokemonType type);

    /**
     * Each won fight means +1 level
     *
     * @param pokemon
     */
    void levelPokemonUp(Pokemon pokemon);
}
