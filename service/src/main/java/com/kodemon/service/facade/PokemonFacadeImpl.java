package com.kodemon.service.facade;

import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.facade.PokemonFacade;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.GymService;
import com.kodemon.service.interfaces.PokemonService;

import javax.inject.Inject;

/**
 * Pokemon facade implementation
 *
 * Renames Pokemon
 *
 * @author Matej Poklemba
 */
public class PokemonFacadeImpl implements PokemonFacade{

    private BeanMappingService beanMappingService;
    private PokemonService pokemonService;

    @Inject
    public PokemonFacadeImpl(
            BeanMappingService beanMappingService,
            PokemonService pokemonService) {
        this.beanMappingService = beanMappingService;
        this.pokemonService = pokemonService;
    }

    @Override
    public void renamePokemon(PokemonDTO pokemon, String newName) {
        pokemonService.renamePokemon(beanMappingService.mapTo(pokemon, Pokemon.class), newName);
    }
}
