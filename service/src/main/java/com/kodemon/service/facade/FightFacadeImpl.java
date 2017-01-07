package com.kodemon.service.facade;

import com.kodemon.api.dto.FightDTO;
import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.enums.WildPokemonFightMode;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.interfaces.*;
import com.kodemon.service.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @author Oliver Roch
 */

@Service
@Transactional
public class FightFacadeImpl implements FightFacade {

    private static final Logger LOG = LoggerFactory.getLogger(FightFacadeImpl.class);

    private BeanMappingService beanMappingService;
    private TrainerFightService trainerFightService;
    private PokemonFightService pokemonFightService;
    private PokemonService pokemonService;
    private TrainerService trainerService;
    private TimeService timeService;

    @Inject
    public FightFacadeImpl(
            BeanMappingService beanMappingService,
            TrainerFightService trainerFightService,
            PokemonFightService pokemonFightService,
            PokemonService pokemonService,
            TrainerService trainerService,
            TimeService timeService) {
        this.beanMappingService = beanMappingService;
        this.trainerFightService = trainerFightService;
        this.pokemonFightService = pokemonFightService;
        this.pokemonService = pokemonService;
        this.trainerService = trainerService;
        this.timeService = timeService;
    }

    @Override
    public boolean fightForBadge(UserDTO user, GymDTO gym) {
        LOG.debug("Beginning fight. User id: {}; Target gym id: {}.", user.getId(), gym.getId());
        return trainerFightService.fightForBadge(beanMappingService.mapTo(user, Trainer.class), beanMappingService.mapTo(gym, Gym.class));
    }

    @Override
    public boolean fightWildPokemon(UserDTO user, PokemonDTO pokemon, WildPokemonFightMode mode) {
        Collection<Trainer> trainers = trainerService.findByUserName(user.getUserName());
        if (trainers.isEmpty())
            return false;
        Trainer trainer = trainers.iterator().next();
        Pokemon wildPokemon = beanMappingService.mapTo(pokemon, Pokemon.class);
        LOG.debug("User {} fighting wild Pokemon in mode {}.", user.getId(), mode);
        Collection<PokemonDTO> trainersPokemons = user.getActivePokemons();
        if (trainersPokemons.isEmpty())
            return false;
        Pokemon trainersPokemon = pokemonService.findById(trainersPokemons.iterator().next().getId());

        Pair<Double, Double> fightScore = pokemonFightService.getScorePair(trainersPokemon, wildPokemon);

        if (fightScore.getX() > fightScore.getY()) {
            if (mode == WildPokemonFightMode.TRAIN) {
                pokemonService.levelPokemonUp(trainersPokemon);
                return true;
            } else if (mode == WildPokemonFightMode.CATCH) {
                pokemonService.assignTrainerToPokemon(trainer, wildPokemon);
                trainerService.addPokemon(wildPokemon, trainer);
                LOG.debug("Adding Pokemon {} to the User {}.", wildPokemon.getId(), user.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<FightDTO> listTodaysFights() {
        return listFightsBetween(timeService.startOfTheDay(timeService.currentDate()), timeService.endOfTheDay(timeService.currentDate()));
    }

    @Override
    public Collection<FightDTO> listThisMonthsFights() {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Date from = new Calendar.Builder().setDate(currentYear, currentMonth, 1).build().getTime();
        return listFightsBetween(from, timeService.currentDate());
    }

    @Override
    public Collection<FightDTO> listThisYearsFights() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Date from = new Calendar.Builder().setDate(currentYear, 1, 1).build().getTime();
        return listFightsBetween(from, timeService.currentDate());
    }

    @Override
    public Collection<FightDTO> listFightsBetween(Date from, Date to) {
        return beanMappingService.mapListTo(trainerFightService.findByFightTimeBetween(from, to), FightDTO.class);
    }

    @Override
    public Collection<FightDTO> listAllFights() {
        return beanMappingService.mapListTo(trainerFightService.findAll(), FightDTO.class);
    }

    @Override
    public Collection<FightDTO> listFightsOfTrainer(UserDTO user) {
        return beanMappingService.mapListTo(trainerFightService.findByChallenger(user.getUserName()), FightDTO.class);
    }

    @Override
    public Collection<FightDTO> listFightsOfGym(GymDTO gym) {
        Gym targetGym = beanMappingService.mapTo(gym, Gym.class);
        return beanMappingService.mapListTo(trainerFightService.findByTargetGymsBadgeName(gym.getBadgeName()), FightDTO.class);
    }

    @Override
    public FightDTO findFightById(Long id) {
        return beanMappingService.mapTo(trainerFightService.findById(id), FightDTO.class);
    }
}
