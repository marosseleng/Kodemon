package com.kodemon.service;

import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;

import java.util.List;

/**
 * Created by mseleng on 11/19/16.
 */
public interface BadgeService {

    // TODO add other CRUD METHODS
    void saveBadge(Badge badge);


    /**
     * Generates new badge for a trainer after successful fight for a gym
     *
     * @param type
     * @return
     */
    Badge getBadgeOfType(PokemonType type);

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

    /**
     * Returns a {@link List} of {@link Badge}s belonging to given {@link Trainer}
     *
     * @param trainer Trainer whose badges to search for
     * @return {@link List} of {@link Badge}s belonging to given {@link Trainer}
     */
    List<Badge> findByTrainer(Trainer trainer);
}
