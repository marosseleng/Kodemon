package com.kodemon.persistence.dao;

import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.persistence.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data Access Object for the Pokemon entity.
 *
 * @author Matej Poklemba
 */
public interface PokemonDao extends JpaRepository<Pokemon, Long> {

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
