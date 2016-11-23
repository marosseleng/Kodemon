/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.dao.PokemonDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.interfaces.GymService;

import javax.inject.Inject;
import java.util.*;


/**
 * Gym service implementation
 * 
 * Can be initialized, saved, deleted and found.
 * 
 * @author Miso Romanek
 */
public class GymServiceImpl implements GymService {

    @Inject
    private GymDao gymDao;

    @Inject
    private TrainerDao trainerDao;

    @Inject
    private PokemonDao pokemonDao;

    @Override
    public void initializeGyms() {
        Pokemon pokemon01 = new Pokemon(PokemonName.GEODUDE);
        Pokemon pokemon02 = new Pokemon(PokemonName.OMANYTE);
        Pokemon pokemon03 = new Pokemon(PokemonName.RHYHORN);
        Pokemon pokemon04 = new Pokemon(PokemonName.GOLEM);
        Pokemon pokemon05 = new Pokemon(PokemonName.ONIX);

        Pokemon pokemon11 = new Pokemon(PokemonName.GOLDEEN);
        Pokemon pokemon12 = new Pokemon(PokemonName.STARYU);
        Pokemon pokemon13 = new Pokemon(PokemonName.SEADRA);
        Pokemon pokemon14 = new Pokemon(PokemonName.WARTORTLE);
        Pokemon pokemon15 = new Pokemon(PokemonName.GOLDUCK);
        Pokemon pokemon16 = new Pokemon(PokemonName.GYARADOS);

        Pokemon pokemon21 = new Pokemon(PokemonName.MAGNEMITE);
        Pokemon pokemon22 = new Pokemon(PokemonName.ELECTRODE);
        Pokemon pokemon23 = new Pokemon(PokemonName.JOLTEON);
        Pokemon pokemon24 = new Pokemon(PokemonName.RAICHU);
        Pokemon pokemon25 = new Pokemon(PokemonName.ELECTABUZZ);

        Pokemon pokemon31 = new Pokemon(PokemonName.GLOOM);
        Pokemon pokemon32 = new Pokemon(PokemonName.TANGELA);
        Pokemon pokemon33 = new Pokemon(PokemonName.PARASECT);
        Pokemon pokemon34 = new Pokemon(PokemonName.VICTREEBEL);
        Pokemon pokemon35 = new Pokemon(PokemonName.VENUSAUR);

        Pokemon pokemon41 = new Pokemon(PokemonName.EKANS);
        Pokemon pokemon42 = new Pokemon(PokemonName.KOFFING);
        Pokemon pokemon43 = new Pokemon(PokemonName.NIDORAN_F);
        Pokemon pokemon44 = new Pokemon(PokemonName.GOLBAT);
        Pokemon pokemon45 = new Pokemon(PokemonName.MUK);
        Pokemon pokemon46 = new Pokemon(PokemonName.NIDOKING);

        Pokemon pokemon51 = new Pokemon(PokemonName.DROWZEE);
        Pokemon pokemon52 = new Pokemon(PokemonName.ABRA);
        Pokemon pokemon53 = new Pokemon(PokemonName.HYPNO);
        Pokemon pokemon54 = new Pokemon(PokemonName.MRMIME);
        Pokemon pokemon55 = new Pokemon(PokemonName.ALAKAZAM);

        Pokemon pokemon61 = new Pokemon(PokemonName.PONYTA);
        Pokemon pokemon62 = new Pokemon(PokemonName.CHARMELEON);
        Pokemon pokemon63 = new Pokemon(PokemonName.FLAREON);
        Pokemon pokemon64 = new Pokemon(PokemonName.ARCANINE);
        Pokemon pokemon65 = new Pokemon(PokemonName.MAGMAR);
        Pokemon pokemon66 = new Pokemon(PokemonName.CHARIZARD);

        Pokemon pokemon71 = new Pokemon(PokemonName.SANDSLASH);
        Pokemon pokemon72 = new Pokemon(PokemonName.CUBONE);
        Pokemon pokemon73 = new Pokemon(PokemonName.MAROWAK);
        Pokemon pokemon74 = new Pokemon(PokemonName.GRAVELER);
        Pokemon pokemon75 = new Pokemon(PokemonName.DUGTRIO);
        Pokemon pokemon76 = new Pokemon(PokemonName.RHYDON);

        pokemonDao.save(pokemon01);
        pokemonDao.save(pokemon02);
        pokemonDao.save(pokemon03);
        pokemonDao.save(pokemon04);
        pokemonDao.save(pokemon05);
        pokemonDao.save(pokemon11);
        pokemonDao.save(pokemon12);
        pokemonDao.save(pokemon13);
        pokemonDao.save(pokemon14);
        pokemonDao.save(pokemon15);
        pokemonDao.save(pokemon16);
        pokemonDao.save(pokemon21);
        pokemonDao.save(pokemon22);
        pokemonDao.save(pokemon23);
        pokemonDao.save(pokemon24);
        pokemonDao.save(pokemon25);
        pokemonDao.save(pokemon31);
        pokemonDao.save(pokemon32);
        pokemonDao.save(pokemon33);
        pokemonDao.save(pokemon34);
        pokemonDao.save(pokemon35);
        pokemonDao.save(pokemon41);
        pokemonDao.save(pokemon42);
        pokemonDao.save(pokemon43);
        pokemonDao.save(pokemon44);
        pokemonDao.save(pokemon45);
        pokemonDao.save(pokemon46);
        pokemonDao.save(pokemon51);
        pokemonDao.save(pokemon52);
        pokemonDao.save(pokemon53);
        pokemonDao.save(pokemon54);
        pokemonDao.save(pokemon55);
        pokemonDao.save(pokemon61);
        pokemonDao.save(pokemon62);
        pokemonDao.save(pokemon63);
        pokemonDao.save(pokemon64);
        pokemonDao.save(pokemon65);
        pokemonDao.save(pokemon66);
        pokemonDao.save(pokemon71);
        pokemonDao.save(pokemon72);
        pokemonDao.save(pokemon73);
        pokemonDao.save(pokemon74);
        pokemonDao.save(pokemon75);
        pokemonDao.save(pokemon76);

        Trainer trainer0 = new Trainer(pokemon01);
        trainer0.addPokemon(pokemon02);
        trainer0.addPokemon(pokemon03);
        trainer0.addPokemon(pokemon04);
        trainer0.addPokemon(pokemon05);

        Trainer trainer1 = new Trainer(pokemon11);
        trainer1.addPokemon(pokemon12);
        trainer1.addPokemon(pokemon13);
        trainer1.addPokemon(pokemon14);
        trainer1.addPokemon(pokemon15);
        trainer1.addPokemon(pokemon16);

        Trainer trainer2 = new Trainer(pokemon21);
        trainer2.addPokemon(pokemon22);
        trainer2.addPokemon(pokemon23);
        trainer2.addPokemon(pokemon24);
        trainer2.addPokemon(pokemon25);

        Trainer trainer3 = new Trainer(pokemon31);
        trainer3.addPokemon(pokemon32);
        trainer3.addPokemon(pokemon33);
        trainer3.addPokemon(pokemon34);
        trainer3.addPokemon(pokemon35);

        Trainer trainer4 = new Trainer(pokemon41);
        trainer4.addPokemon(pokemon42);
        trainer4.addPokemon(pokemon43);
        trainer4.addPokemon(pokemon44);
        trainer4.addPokemon(pokemon45);
        trainer4.addPokemon(pokemon46);

        Trainer trainer5 = new Trainer(pokemon51);
        trainer5.addPokemon(pokemon52);
        trainer5.addPokemon(pokemon53);
        trainer5.addPokemon(pokemon54);
        trainer5.addPokemon(pokemon55);

        Trainer trainer6 = new Trainer(pokemon61);
        trainer6.addPokemon(pokemon62);
        trainer6.addPokemon(pokemon63);
        trainer6.addPokemon(pokemon64);
        trainer6.addPokemon(pokemon65);
        trainer6.addPokemon(pokemon66);

        Trainer trainer7 = new Trainer(pokemon71);
        trainer7.addPokemon(pokemon72);
        trainer7.addPokemon(pokemon73);
        trainer7.addPokemon(pokemon74);
        trainer7.addPokemon(pokemon75);
        trainer7.addPokemon(pokemon76);

        trainerDao.save(trainer0);
        trainerDao.save(trainer1);
        trainerDao.save(trainer2);
        trainerDao.save(trainer3);
        trainerDao.save(trainer4);
        trainerDao.save(trainer5);
        trainerDao.save(trainer6);
        trainerDao.save(trainer7);

        Gym gym0 = new Gym(trainer0);
        Gym gym1 = new Gym(trainer1);
        Gym gym2 = new Gym(trainer2);
        Gym gym3 = new Gym(trainer3);
        Gym gym4 = new Gym(trainer4);
        Gym gym5 = new Gym(trainer5);
        Gym gym6 = new Gym(trainer6);
        Gym gym7 = new Gym(trainer7);

        gymDao.save(gym0);
        gymDao.save(gym1);
        gymDao.save(gym2);
        gymDao.save(gym3);
        gymDao.save(gym4);
        gymDao.save(gym5);
        gymDao.save(gym6);
        gymDao.save(gym7);
    }

    @Override
    public void save(Gym gym) {
        gymDao.save(gym);
    }

    @Override
    public void delete(Gym gym) {
        gymDao.delete(gym);
    }

    @Override
    public List<Gym> findByCity(String city) {
        return gymDao.findByCity(city);
    }

    @Override
    public List<Gym> findByCityLike(String city) {
        return gymDao.findByCityLike(city);
    }

    @Override
    public List<Gym> findByCityContaining(String string) {
        return gymDao.findByCityContaining(string);
    }

    @Override
    public List<Gym> findByType(PokemonType type) {
        return gymDao.findByType(type);
    }

    @Override
    public List<Gym> findByTrainer(Trainer trainer) {
        return gymDao.findByTrainer(trainer);
    }
    
}
