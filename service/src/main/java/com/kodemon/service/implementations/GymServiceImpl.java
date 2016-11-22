/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
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

    @Override
    public void initializeGyms() {
        Pokemon pokemon1 = new Pokemon();
        Pokemon pokemon2 = new Pokemon();
        Pokemon pokemon3 = new Pokemon();
        Pokemon pokemon4 = new Pokemon();
        Pokemon pokemon5 = new Pokemon();
        Pokemon pokemon6 = new Pokemon();
        Pokemon pokemon7 = new Pokemon();
        Pokemon pokemon8 = new Pokemon();
        Trainer trainer1 = new Trainer(pokemon1);
        Trainer trainer2 = new Trainer(pokemon2);
        Trainer trainer3 = new Trainer(pokemon3);
        Trainer trainer4 = new Trainer(pokemon4);
        Trainer trainer5 = new Trainer(pokemon5);
        Trainer trainer6 = new Trainer(pokemon6);
        Trainer trainer7 = new Trainer(pokemon7);
        Trainer trainer8 = new Trainer(pokemon8);
        Gym gym1 = new Gym(trainer1);
        Gym gym2 = new Gym(trainer2);
        Gym gym3 = new Gym(trainer3);
        Gym gym4 = new Gym(trainer4);
        Gym gym5 = new Gym(trainer5);
        Gym gym6 = new Gym(trainer6);
        Gym gym7 = new Gym(trainer7);
        Gym gym8 = new Gym(trainer8);        
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
