package com.kodemon.service.facade;

import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.PokemonFacade;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Pokemon facade implementation
 * <p>
 * Renames Pokemon
 *
 * @author Matej Poklemba
 */
@Service
@Transactional
public class PokemonFacadeImpl implements PokemonFacade {

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

    @Override
    public PokemonDTO generateWildPokemon(UserDTO user) {
        return beanMappingService.mapTo(pokemonService.generateWildPokemon(null, user.getPokemons().get(0).getLevel() - 5, user.getPokemons().get(0).getLevel() + 5), PokemonDTO.class);
    }

}
