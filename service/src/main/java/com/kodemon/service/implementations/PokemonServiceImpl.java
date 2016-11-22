package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.interfaces.PokemonService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.*;

/**
 * Pokemon Service implementation
 *
 * Can generate wild Pokemon for encounters, level up Pokemon, assign Trainer to a Pokemon
 *
 * @author Matej Poklemba
 */
public class PokemonServiceImpl implements PokemonService {

    @Inject
    private PokemonDao pokemonDao;

    private static final Random random = new Random();
    @Override
    public Pokemon generateWildPokemon(@Nullable PokemonType type) {
        Pokemon pokemon;
        if (type == null) {
            int i = random.nextInt(PokemonName.class.getEnumConstants().length);
            pokemon = new Pokemon(PokemonName.class.getEnumConstants()[i]);
        }
        else {
            List<PokemonName> pokemons = Arrays.asList(PokemonName.class.getEnumConstants());
            List<PokemonName> pokemonsOfWantedType = new ArrayList<>();
            for (int i = 0; i < pokemons.size(); i++) {
                PokemonName p = pokemons.get(i);
                if (Arrays.asList(pokemons.get(i).getTypes()).indexOf(type) != -1)
                    pokemonsOfWantedType.add(p);
            }
            int i = random.nextInt(pokemonsOfWantedType.size());
            pokemon = new Pokemon(pokemonsOfWantedType.get(i));
        }
        return pokemon;
    }

    @Override
    public void levelPokemonUp(Pokemon pokemon) {
        pokemon.setLevel(pokemon.getLevel() + 1);
        pokemonDao.save(pokemon);
    }

    @Override
    public void assignTrainerToPokemon(Trainer trainer, Pokemon pokemon) {
        pokemon.setTrainer(trainer);
        pokemonDao.save(pokemon);
    }

    @Override
    public void save(Pokemon pokemon) {
        pokemonDao.save(pokemon);
    }

    @Override
    public void delete(Pokemon pokemon) {
        pokemonDao.delete(pokemon);
    }

    @Override
    public List<Pokemon> findByTrainer(Trainer trainer) {
        return pokemonDao.findByTrainer(trainer);
    }

    @Override
    public List<Pokemon> findByName(PokemonName name) {
        return pokemonDao.findByName(name);
    }

    @Override
    public List<Pokemon> findByNicknameStartingWith(String prefix) {
        return pokemonDao.findByNicknameStartingWith(prefix);
    }

    @Override
    public List<Pokemon> findByNickname(String nickname) {
        return pokemonDao.findByNickname(nickname);
    }

    @Override
    public List<Pokemon> findByLevel(int level) {
        return pokemonDao.findByLevel(level);
    }
}
