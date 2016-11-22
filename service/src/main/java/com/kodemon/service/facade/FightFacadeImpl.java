package com.kodemon.service.facade;

import com.kodemon.api.dto.FightDTO;
import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.enums.WildPokemonFightMode;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import com.kodemon.service.interfaces.*;
import com.kodemon.service.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Oliver Roch
 */

@Service
@Transactional
public class FightFacadeImpl implements FightFacade {
    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private TrainerFightService trainerFightService;

    @Autowired
    private PokemonFightService pokemonFightService;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private BadgeService badgeService;


    @Override
    public void fightForBadge(UserDTO user, GymDTO gym) {
        Trainer challengingTrainer = beanMappingService.mapTo(user, Trainer.class);
        Gym targetGym = beanMappingService.mapTo(gym, Gym.class);

        boolean wasChallengerSuccessful = false;
        if(trainerFightService.wasFightForBadgeSuccessful(challengingTrainer, targetGym.getTrainer())) {
            challengingTrainer.addBadge(badgeService.createBadgeOfType(targetGym.getType()));
            wasChallengerSuccessful = true;
        }

        FightDTO fight = new FightDTO();
        fight.setTargetGym(gym);
        fight.setChallenger(user);
        fight.setFightTime(new Date());
        fight.setWasChallengerSuccessful(wasChallengerSuccessful);
        trainerFightService.save(beanMappingService.mapTo(fight, TrainerFight.class));
    }

    @Override
    public void fightWildPokemon(UserDTO user, WildPokemonFightMode mode) {
        Trainer trainer = beanMappingService.mapTo(user, Trainer.class);
        Pokemon wildPokemon = pokemonService.generateWildPokemon(null);
        Pokemon trainersPokemon = trainer.getPokemons().get(0);

        Pair<Double, Double> fightScore = pokemonFightService.getScorePair(trainersPokemon, wildPokemon);

        if(fightScore.getX() > fightScore.getY()) {
            if(mode == WildPokemonFightMode.TRAIN) {
                pokemonService.levelPokemonUp(trainersPokemon);
            } else if(mode == WildPokemonFightMode.CATCH) {
                trainerService.addPokemon(wildPokemon, trainer);
            }
        }
    }

    @Override
    public List<FightDTO> listTodaysFights() {
        return listFightsBetween(new Date(), new Date());
    }

    @Override
    public List<FightDTO> listThisMonthsFights() {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Date from = new Calendar.Builder().setDate(currentYear, currentMonth, 1).build().getTime();
        return listFightsBetween(from, new Date());
    }

    @Override
    public List<FightDTO> listThisYearsFights() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Date from = new Calendar.Builder().setDate(currentYear, 1, 1).build().getTime();
        return listFightsBetween(from, new Date());
    }

    @Override
    public List<FightDTO> listFightsBetween(Date from, Date to) {
        return beanMappingService.mapTo(trainerFightService.findByFightTimeBetween(from, to), FightDTO.class);
    }

    @Override
    public List<FightDTO> listAllFights() {
        return beanMappingService.mapTo(trainerFightService.findAll(), FightDTO.class);
    }

    @Override
    public List<FightDTO> listFightsOfTrainer(UserDTO user) {
        Trainer challenger = beanMappingService.mapTo(user, Trainer.class);
        return beanMappingService.mapTo(trainerFightService.findByChallenger(challenger), FightDTO.class);
    }

    @Override
    public List<FightDTO> listFightsOfGym(GymDTO gym) {
        Gym targetGym = beanMappingService.mapTo(gym, Gym.class);
        return beanMappingService.mapTo(trainerFightService.findByTargetGym(targetGym), FightDTO.class);
    }
}
