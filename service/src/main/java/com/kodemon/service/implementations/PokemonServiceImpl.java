package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.interfaces.PokemonService;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Pokemon Service implementation
 * <p>
 * Can generate wild Pokemon for encounters, level up Pokemon, assign Trainer to a Pokemon
 *
 * @author Matej Poklemba
 */
@Service
public class PokemonServiceImpl implements PokemonService {

    private static final Random RANDOM = new Random();

    private PokemonDao pokemonDao;

    @Inject
    public PokemonServiceImpl(PokemonDao pokemonDao) {
        this.pokemonDao = pokemonDao;
    }

    @Override
    public Pokemon generateWildPokemon(@Nullable PokemonType type, int minLevel, int maxLevel) {
        Pokemon pokemon;
        if (type == null) {
            int i = RANDOM.nextInt(PokemonName.values().length);
            pokemon = new Pokemon(PokemonName.values()[i]);
        } else {
            List<PokemonName> pokemons = Arrays.asList(PokemonName.values());
            List<PokemonName> pokemonsOfWantedType = new ArrayList<>();
            for (int i = 0; i < pokemons.size(); i++) {
                PokemonName p = pokemons.get(i);
                if (Arrays.asList(pokemons.get(i).getTypes()).indexOf(type) != -1)
                    pokemonsOfWantedType.add(p);
            }
            int i = RANDOM.nextInt(pokemonsOfWantedType.size());
            pokemon = new Pokemon(pokemonsOfWantedType.get(i));
        }
        Random rand = new Random();
        pokemon.setLevel(Math.max(1, minLevel + rand.nextInt(maxLevel - minLevel + 1)));
        return pokemon;
    }

    @Override
    public Pokemon createPokemonWithName(PokemonName name) {
        return new Pokemon(name);
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
    public void renamePokemon(Pokemon pokemon, String newName) {
        pokemon.setNickname(newName);
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
    public Pokemon findById(Long id) {
        return pokemonDao.findOne(id);
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
