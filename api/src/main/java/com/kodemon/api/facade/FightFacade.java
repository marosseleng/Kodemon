package com.kodemon.api.facade;

import com.kodemon.api.dto.FightDTO;
import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.enums.WildPokemonFightMode;

import java.util.Date;
import java.util.List;

/**
 * Facade providing access to the fight system
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface FightFacade {

    /**
     * Represents an attempt to conquer the {@link com.kodemon.persistence.entity.Gym}
     *
     * @param user user that wants to conquer the gym
     * @param gym gym to be challenged
     */
    void fightForBadge(UserDTO user, GymDTO gym);

    /**
     * Fights a wild {@link com.kodemon.persistence.entity.Pokemon}
     * <p/>
     * A fight can be done according to two scenarios:
     * <ol>
     *     <li>The {@link com.kodemon.persistence.entity.Trainer} want to catch a wild {@link com.kodemon.persistence.entity.Pokemon}</li>
     *     <li>The {@link com.kodemon.persistence.entity.Trainer} want to exercise his {@link com.kodemon.persistence.entity.Pokemon} without catching the wild one.</li>
     * </ol>
     *
     * @param user user wanting to fight a wild Pokemon
     * @param mode {@link WildPokemonFightMode} saying what the trainer wants
     */
    void fightWildPokemon(UserDTO user, WildPokemonFightMode mode);

    /**
     * Returns fights, which has been played today
     *
     * @return a {@link List} of {@link FightDTO}s
     */
    List<FightDTO> listTodaysFights();

    /**
     * Returns fights, which has been played this month
     *
     * @return a {@link List} of {@link FightDTO}s
     */
    List<FightDTO> listThisMonthsFights();

    /**
     * Returns fights, which has been played this year
     *
     * @return a {@link List} of {@link FightDTO}s
     */
    List<FightDTO> listThisYearsFights();

    /**
     * Returns fights that happened between two specified dates
     *
     * @param from start date from which to list fights
     * @param to end date to which to list fights
     * @return a {@link List} of {@link FightDTO}s
     */
    List<FightDTO> listFightsBetween(Date from, Date to);

    /**
     * Returns all fights that ever ocurred
     *
     * @return a {@link List} of {@link FightDTO}s
     */
    List<FightDTO> listAllFights();

    /**
     * Returns all fights of the given user
     *
     * @param user user, whose fights we want to find
     * @return a {@link List} of {@link FightDTO}s
     */
    List<FightDTO> listFightsOfTrainer(UserDTO user);

    /**
     * Returns all fights that was hosted in the given {@link com.kodemon.persistence.entity.Gym}
     *
     * @param gym gym that hosted the fights
     * @return a {@link List} of {@link FightDTO}s
     */
    List<FightDTO> listFightsOfGym(GymDTO gym);
}

