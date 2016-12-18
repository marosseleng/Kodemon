package com.kodemon.api.facade;

import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserDTO;

/**
 * Facade providing access to the Pokemon administration
 *
 * @author Matej Poklemba
 */
public interface PokemonFacade {

    /**
     * Renames given Pokemon and updates record in Pokemon table.
     *
     * @param pokemon Pokemon to be renamed
     * @param newName Desired new nickname of the Pokemon
     */
    void renamePokemon(PokemonDTO pokemon, String newName);

    /**
     * Generates a wild pokemon with level around user's primary pokemon's level
     *
     * @param user User to be taken into consideration
     * @return random Pokemon
     */
    PokemonDTO generateWildPokemon(UserDTO user);
}

