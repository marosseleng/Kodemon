package com.kodemon.persistence.dao;

import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Data Access Object for the TrainerFight entity.
 *
 * @author Oliver Roch
 */
@Repository
public interface TrainerFightDao extends JpaRepository<TrainerFight, Long> {
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
}
