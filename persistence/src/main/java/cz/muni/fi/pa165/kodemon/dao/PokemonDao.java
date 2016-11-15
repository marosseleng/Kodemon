package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.Pokemon;
import cz.muni.fi.pa165.kodemon.entity.Trainer;
import cz.muni.fi.pa165.kodemon.enums.PokemonType;
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
     * Returns a {@link List} of {@link Pokemon}s with given name
     *
     * @param name Name to search for
     * @return {@link List} of {@link Pokemon}s with given name
     */
    List<Pokemon> findByName(String name);

    /**
     * Returns a {@link List} of {@link Pokemon}s with name starting with the given prefix.
     * <p/>
     * Note: the prefix <b>must</b> be appended by {@code %} as the method generates the following query:
     * {@code … where x.name like ?1}
     *
     * @param prefix Name prefix to search by
     * @return {@link List} of {@link Pokemon}s with names prefixed with the given prefix
     */
    List<Pokemon> findByNameStartingWith(String prefix);

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
     * Returns a {@link List} of {@link Pokemon}s with given {@link PokemonType}
     *
     * @param type PokemonType to search for
     * @return {@link List} of {@link Pokemon}s with given type
     */
    List<Pokemon> findByType(PokemonType type);

    /**
     * Returns a {@link List} of {@link Pokemon}s with given level
     *
     * @param level Level to search for
     * @return {@link List} of {@link Pokemon}s with given level
     */
    List<Pokemon> findByLevel(int level);
}
