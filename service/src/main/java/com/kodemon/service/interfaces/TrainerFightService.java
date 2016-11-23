package com.kodemon.service.interfaces;

import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;

import java.util.Date;
import java.util.List;

/**
 * Service handling fights between {@link Trainer}s
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface TrainerFightService {

    /**
     * Performs fight between two {@link Trainer}s (while conquering a {@link com.kodemon.persistence.entity.Gym})
     * <p/>
     * Iterates through both trainers' most powerful Pokemon and performs a Pokemon fight on each couple.
     * Then sums up points and creates the {@link com.kodemon.persistence.entity.TrainerFight} instance
     * This method also takes care of giving new badge to the challenger if he wins
     *
     * @param challenger trainer that wants to conquer the gym
     * @param defender trainer that defends the gym
     */
    void fightForBadge(Trainer challenger, Trainer defender);

    /**
     * Find all fights where challenger is specified trainer
     *
     * @param challenger trainer, whose fights as challenger we want to find
     * @return all fights, where specified trainer was as challenger
     */
    List<TrainerFight> findByChallenger(Trainer challenger);

    /**
     * Find all fights for the specified gym
     *
     * @param targetGym gym for which the fights were
     * @return list of fights for the specified gym
     */
    List<TrainerFight> findByTargetGym(Gym targetGym);

    /**
     * Find all fight between specified times
     *
     * @param from bottom bound for the fight time
     * @param til upper bound for the fight time
     * @return list of all fights between specified times
     */
    List<TrainerFight> findByFightTimeBetween(Date from, Date til);

    /**
     * Returns all {@link TrainerFight}s from the database
     *
     * @return a {@link List} of all {@link TrainerFight}s in the database
     */
    List<TrainerFight> findAll();
}
