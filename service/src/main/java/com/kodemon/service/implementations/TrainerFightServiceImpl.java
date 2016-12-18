package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.dao.TrainerFightDao;
import com.kodemon.persistence.entity.*;
import com.kodemon.service.interfaces.*;
import com.kodemon.service.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Service for TrainerFights. It can find you fights played at specified time range and simulate fight,
 * determine, whether match for a badge was successful and save the result.
 *
 * @author Oliver Roch
 */

@Service
public class TrainerFightServiceImpl implements TrainerFightService {

    public static final int AMOUNT_OF_POKEMONS_FOR_MATCH = 6;
    private static final Logger LOG = LoggerFactory.getLogger(TrainerFightServiceImpl.class);

    private TrainerDao trainerDao;
    private TrainerFightDao trainerFightDao;
    private PokemonFightService pokemonFightService;
    private BadgeService badgeService;
    private TrainerService trainerService;
    private TimeService timeService;
    private GymService gymService;
    private GymDao gymDao;

    @Inject
    public TrainerFightServiceImpl(TrainerDao trainerDao, TrainerFightDao trainerFightDao, PokemonFightService pokemonFightService, GymDao gymDao, BadgeService badgeService, TrainerService trainerService, TimeService timeService, GymService gymService) {
        this.trainerDao = trainerDao;
        this.trainerFightDao = trainerFightDao;
        this.pokemonFightService = pokemonFightService;
        this.gymDao = gymDao;
        this.badgeService = badgeService;
        this.trainerService = trainerService;
        this.timeService = timeService;
        this.gymService = gymService;
    }

    @Override
    public boolean wasFightForBadgeSuccessful(Trainer challenger, Trainer defender) {
        double challengerScore = 0;
        double defenderScore = 0;

        for (int i = 0; i < AMOUNT_OF_POKEMONS_FOR_MATCH; i++) {
            Pair<Double, Double> scorePair;
            if (challenger.getPokemons().size() > i && defender.getPokemons().size() > i) {
                Pokemon currentChallengerPokemon = challenger.getPokemons().get(i);
                Pokemon currentDefenderPokemon = defender.getPokemons().get(i);
                scorePair = pokemonFightService.getScorePair(currentChallengerPokemon, currentDefenderPokemon);
            } else if (challenger.getPokemons().size() <= i && defender.getPokemons().size() > i) {
                scorePair = new Pair<>(0.0, (double) defender.getPokemons().get(i).getLevel());
            } else if (challenger.getPokemons().size() > i && defender.getPokemons().size() <= i) {
                scorePair = new Pair<>((double) challenger.getPokemons().get(i).getLevel(), 0.0);
            } else {
                scorePair = new Pair<>(0.0, 0.0);
            }

            challengerScore += scorePair.getX();
            defenderScore += scorePair.getY();
        }

        return challengerScore > defenderScore;
    }

    @Override
    public TrainerFight findById(Long id) {
        return trainerFightDao.findOne(id);
    }

    @Override
    public List<TrainerFight> findByChallenger(String username) {
        Collection<Trainer> challenger = trainerDao.findByUserName(username);
        return trainerFightDao.findByChallenger(challenger.iterator().next());
    }

    @Override
    public List<TrainerFight> findByTargetGymsBadgeName(String badgeName) {
        Collection<Gym> gyms = gymDao.findByBadgeName(badgeName);
        if (gyms.isEmpty()) {
            return null;
        }
        return trainerFightDao.findByTargetGym(gyms.iterator().next());
    }

    @Override
    public List<TrainerFight> findByFightTimeBetween(Date from, Date til) {
        return trainerFightDao.findByFightTimeBetween(from, til);
    }

    @Override
    public List<TrainerFight> findAll() {
        return trainerFightDao.findAll();
    }

    @Override
    public void save(TrainerFight trainerFight) {
        trainerFightDao.save(trainerFight);
    }

    @Override
    public boolean fightForBadge(Trainer trainer, Gym gym) {
        boolean wasChallengerSuccessful = false;
        Collection<Trainer> trainers = trainerService.findByUserName(trainer.getUserName());
        if (trainers.isEmpty())
            return false;
        trainer = trainers.iterator().next();
        Collection<Gym> gyms = gymService.findByBadgeName(gym.getBadgeName());
        if (gyms.isEmpty())
            return false;
        gym = gyms.iterator().next();
        if (this.wasFightForBadgeSuccessful(trainer, gym.getTrainer())) {
            Badge badge = badgeService.createBadgeOfGym(gym);
            badgeService.save(badge);
            trainerService.addBadge(badge, trainer);
            wasChallengerSuccessful = true;
        }

        TrainerFight fight = new TrainerFight();
        fight.setTargetGym(gym);
        fight.setChallenger(trainer);
        fight.setWasChallengerSuccessful(wasChallengerSuccessful);
        Date fightTime = timeService.currentDate();
        fight.setFightTime(fightTime);
        String successString = wasChallengerSuccessful ? "Success" : "Failed";
        LOG.debug("Storing the result (" + successString + ") of the fight between User {} and Gym {}. Fight time: {}.", trainer.getId(), gym.getId(), fightTime);
        this.save(fight);
        return wasChallengerSuccessful;
    }
}
