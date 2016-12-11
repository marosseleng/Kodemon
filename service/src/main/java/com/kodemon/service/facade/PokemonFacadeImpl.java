package com.kodemon.service.facade;

import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.facade.PokemonFacade;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Pokemon facade implementation
 *
 * Renames Pokemon
 *
 * @author Matej Poklemba
 */
public class PokemonFacadeImpl implements PokemonFacade{

    private static final Logger LOG = LoggerFactory.getLogger(PokemonFacadeImpl.class);

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
        LOG.debug("Renaming Pokemon {}. New name: {}", pokemon, newName);
        pokemonService.renamePokemon(beanMappingService.mapTo(pokemon, Pokemon.class), newName);
    }
}
