package com.kodemon.api.facade;

import com.kodemon.api.dto.FightDTO;
import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.enums.WildPokemonFightMode;

import java.util.Date;
import java.util.List;

/**
 * Created by mseleng on 11/19/16.
 */
public interface FightFacade {
    /**
     * Represents an attempt to conquer the gym
     *
     * @param user
     * @param gym
     */
    void fightForBadge(UserDTO user, GymDTO gym);

    void fightWildPokemon(UserDTO user, WildPokemonFightMode mode);

    List<FightDTO> listFightsBetween(Date form, Date to);

    List<FightDTO> listAllFights();

    List<FightDTO> listFightsOfTrainer(UserDTO user);

    List<FightDTO> listFightsOfGym(GymDTO gym);
}

