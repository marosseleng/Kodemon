package com.kodemon.service.facade;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.enums.WildPokemonFightMode;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.service.PokemonService;
import com.kodemon.service.TrainerFightService;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by mseleng on 11/19/16.
 */
public class FightFacadeImpl implements FightFacade {
    @Inject
    TrainerFightService service;

    @Inject
    PokemonService service1;

    @Override
    public void fightForBadge(UserDTO user, GymDTO gym) {
        service.fightForBadge();
    }

    @Override
    public void fightWildPokemon(UserDTO user, WildPokemonFightMode mode) {

    }

    @Override
    public void listFightsBetween(Date form, Date to) {

    }

    @Override
    public void listAllFights() {

    }

    @Override
    public void listFightsOfTrainer(UserDTO user) {

    }

    @Override
    public void listFightsOfGym(GymDTO gym) {

    }
}
