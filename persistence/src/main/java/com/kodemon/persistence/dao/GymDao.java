package com.kodemon.persistence.dao;

import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for the Gym entity.
 *
 * @author Miso Romanek
 */
@Repository
public interface GymDao extends JpaRepository<Gym, Long> {

    /**
     * Returns a {@link List} of {@link Gym}s with the given username.
     *
     * @param city City to search for.
     * @return {@link List} of {@link Gym}s in the given city.
     */
    List<Gym> findByCity(String city);

    /**
     * Returns a {@link List} of {@link Gym}s with similar cities to the given one.
     * <p/>
     * Note: this method generates the following query: {@code … where x.city like ?1}
     *
     * @param city City to search for.
     * @return {@link List} of {@link Gym}s with similar cities to the given one.
     */
    List<Gym> findByCityLike(String city);


    /**
     * Returns a {@link List} of {@link Gym}s where the city contains the given string.
     * <p/>
     * Note: a parameter <b>must</b> be wrapped in {@code %} as the method generates the following query:
     * {@code … where x.city like ?1}
     *
     * @param string String contained in {@link Gym}'s city.
     * @return {@link List} of {@link Gym}s with cities containing the given string.
     */
    List<Gym> findByCityContaining(String string);

    /**
     * Returns a {@link List} of {@link Gym}s with the given badge name.
     *
     * @param badgeName Badge name to search for.
     * @return {@link List} of {@link Gym}s with the given badge name.
     */
    List<Gym> findByBadgeName(String badgeName);

    /**
     * Returns a {@link List} of {@link Gym}s with similar badge names to the given one.
     * <p/>
     * Note: this method generates the following query: {@code … where x.badgeName like ?1}
     *
     * @param badgeName Badge name to search for.
     * @return {@link List} of {@link Gym}s with similar badge names to the given one.
     */
    List<Gym> findByBadgeNameLike(String badgeName);


    /**
     * Returns a {@link List} of {@link Gym}s where the badge name contains the given string.
     * <p/>
     * Note: a parameter <b>must</b> be wrapped in {@code %} as the method generates the following query:
     * {@code … where x.badgeName like ?1}
     *
     * @param string String contained in {@link Gym}'s badge name.
     * @return {@link List} of {@link Gym}s with badge name containing the given string.
     */
    List<Gym> findByBadgeNameContaining(String string);
    
    /**
     * Returns a {@link List} of {@link Gym}s with given PokemonType.
     *
     * @param type {@link PokemonType} to search for.
     * @return {@link List} of {@link Gym}s of the given type.
     */
    List<Gym> findByType(PokemonType type);
    
    /**
     * Returns a {@link List} of {@link Gym}s of the given trainer.
     * <p/>
     * Note: The list always contains only one gym.
     *
     * @param trainer {@link Trainer} to search for.
     * @return {@link List} of {@link Gym}s of the given trainer.
     */
    List<Gym> findByTrainer(Trainer trainer);
}
