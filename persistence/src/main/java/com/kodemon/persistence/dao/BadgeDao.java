package com.kodemon.persistence.dao;

import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for the Badge entity.
 *
 * @author Oliver Roch
 */
@Repository
public interface BadgeDao extends JpaRepository<Badge, Long> {

    /**
     * Returns a {@link List} of {@link Badge}s with given name
     *
     * @param name Name to search for
     * @return {@link List} of {@link Badge}s with given name
     */
    List<Badge> findByName(String name);

    /**
     * Returns a {@link List} of {@link Badge}s with name starting with the given prefix.
     * <p/>
     * Note: the prefix <b>must</b> be appended by {@code %} as the method generates the following query:
     * {@code … where x.name like ?1}
     *
     * @param prefix Name prefix to search by
     * @return {@link List} of {@link Badge}s with names prefixed with the given prefix
     */
    List<Badge> findByNameStartingWith(String prefix);

    /**
     * Returns a {@link List} of {@link Badge}s belonging to given {@link Gym}
     *
     * @param gym Gym whose badges to search for
     * @return {@link List} of {@link Badge}s belonging to given {@link Gym}
     */
    List<Badge> findByGym(Gym gym);
}
