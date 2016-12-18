package com.kodemon.service.interfaces;

import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Service for doing stuff with {@link Pokemon}s
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface PokemonService {

    /**
     * Generates random wild Pokemon of the given type.
     * A wild {@link com.kodemon.persistence.entity.Pokemon} has no {@link com.kodemon.persistence.entity.Trainer}.
     * <p/>
     * When the {@param type} is {@code null}, Pokemon type is random also.
     * <p/>
     * The only way a Pokemon can be created is through this method.
     *
     * @param type     (optional) type of the Pokemon
     * @param minLevel minimum level of the Pokemon
     * @param maxLevel maximum level of the Pokemon
     * @return generated wild Pokemon
     */
    Pokemon generateWildPokemon(@Nullable PokemonType type, int minLevel, int maxLevel);

    /**
     * Levels up the given Pokemon
     * <p/>
     * Basically just increases the given Pokemon's level by one and updates the database record.
     *
     * @param pokemon Pokemon to be leveled up
     */
    void levelPokemonUp(Pokemon pokemon);

    /**
     * Assigns the given trainer to the given Pokemon and updates record in Pokemon table.
     * <p/>
     * The method does <b>not</b> add the Pokemon to the trainer's list of Pokemon
     *
     * @param trainer trainer to be assigned to the pokemon
     * @param pokemon Pokemon that is going to have a new trainer
     */
    void assignTrainerToPokemon(Trainer trainer, Pokemon pokemon);

    /**
     * Renames given Pokemon and updates record in Pokemon table.
     *
     * @param pokemon Pokemon to be renamed
     * @param newName Desired new nickname of the Pokemon
     */
    void renamePokemon(Pokemon pokemon, String newName);

    /**
     * Saves the given Pokemon to the database
     *
     * @param pokemon Pokemon to be saved
     */
    void save(Pokemon pokemon);

    /**
     * Deletes the given Pokemon from the database
     *
     * @param pokemon Pokemon to be deleted
     */
    void delete(Pokemon pokemon);

    /**
     * Returns a {@link List} of {@link Pokemon}s belonging to given (@link Trainer)
     *
     * @param trainer Trainer to search belonging to for
     * @return {@link List} of {@link Pokemon}s belonging to given trainer
     */
    List<Pokemon> findByTrainer(Trainer trainer);

    /**
     * Returns a {@link List} of {@link Pokemon}s with given {@link PokemonName}
     *
     * @param name Name to search for
     * @return {@link List} of {@link Pokemon}s with given name
     */
    List<Pokemon> findByName(PokemonName name);

    /**
     * Returns a {@link List} of {@link Pokemon}s with nickname starting with the given prefix.
     * <p/>
     * Note: the prefix <b>must</b> be appended by {@code %} as the method generates the following query:
     * {@code … where x.nickname like ?1}
     *
     * @param prefix Nickname prefix to search by
     * @return {@link List} of {@link Pokemon}s with nicknames prefixed with the given prefix
     */
    List<Pokemon> findByNicknameStartingWith(String prefix);

    /**
     * Returns a {@link List} of {@link Pokemon}s with given nickname
     *
     * @param nickname Nickname to search for
     * @return {@link List} of {@link Pokemon}s with given nickname
     */
    List<Pokemon> findByNickname(String nickname);

    /**
     * Returns a {@link List} of {@link Pokemon}s with given level
     *
     * @param level Level to search for
     * @return {@link List} of {@link Pokemon}s with given level
     */
    List<Pokemon> findByLevel(int level);
}
