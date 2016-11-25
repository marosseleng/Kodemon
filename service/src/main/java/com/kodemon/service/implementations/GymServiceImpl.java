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
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;


/**
 * Gym service implementation
 * 
 * Can initialize the whole setting with 8 gyms of different types, with corresponding trainers, cities, pokemons and their levels.
 * 
 * Can also be saved, deleted and found.
 * 
 * @author Miso Romanek
 */
@Service
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
        pokemon01.setLevel(15);
        pokemon02.setLevel(16);
        pokemon03.setLevel(14);
        pokemon04.setLevel(16);
        pokemon05.setLevel(18);

        Pokemon pokemon11 = new Pokemon(PokemonName.GOLDEEN);
        Pokemon pokemon12 = new Pokemon(PokemonName.STARYU);
        Pokemon pokemon13 = new Pokemon(PokemonName.SEADRA);
        Pokemon pokemon14 = new Pokemon(PokemonName.WARTORTLE);
        Pokemon pokemon15 = new Pokemon(PokemonName.GOLDUCK);
        Pokemon pokemon16 = new Pokemon(PokemonName.GYARADOS);
        pokemon11.setLevel(22);
        pokemon12.setLevel(24);
        pokemon13.setLevel(21);
        pokemon14.setLevel(23);
        pokemon15.setLevel(25);
        pokemon16.setLevel(25);

        Pokemon pokemon21 = new Pokemon(PokemonName.MAGNEMITE);
        Pokemon pokemon22 = new Pokemon(PokemonName.ELECTRODE);
        Pokemon pokemon23 = new Pokemon(PokemonName.JOLTEON);
        Pokemon pokemon24 = new Pokemon(PokemonName.RAICHU);
        Pokemon pokemon25 = new Pokemon(PokemonName.ELECTABUZZ);
        pokemon21.setLevel(28);
        pokemon22.setLevel(31);
        pokemon23.setLevel(31);
        pokemon24.setLevel(32);
        pokemon25.setLevel(34);

        Pokemon pokemon31 = new Pokemon(PokemonName.GLOOM);
        Pokemon pokemon32 = new Pokemon(PokemonName.TANGELA);
        Pokemon pokemon33 = new Pokemon(PokemonName.PARASECT);
        Pokemon pokemon34 = new Pokemon(PokemonName.VICTREEBEL);
        Pokemon pokemon35 = new Pokemon(PokemonName.VENUSAUR);
        pokemon31.setLevel(36);
        pokemon32.setLevel(35);
        pokemon33.setLevel(37);
        pokemon34.setLevel(37);
        pokemon35.setLevel(38);

        Pokemon pokemon41 = new Pokemon(PokemonName.EKANS);
        Pokemon pokemon42 = new Pokemon(PokemonName.KOFFING);
        Pokemon pokemon43 = new Pokemon(PokemonName.NIDORAN_F);
        Pokemon pokemon44 = new Pokemon(PokemonName.GOLBAT);
        Pokemon pokemon45 = new Pokemon(PokemonName.MUK);
        Pokemon pokemon46 = new Pokemon(PokemonName.NIDOKING);
        pokemon41.setLevel(40);
        pokemon42.setLevel(43);
        pokemon43.setLevel(43);
        pokemon44.setLevel(42);
        pokemon45.setLevel(44);
        pokemon46.setLevel(45);

        Pokemon pokemon51 = new Pokemon(PokemonName.DROWZEE);
        Pokemon pokemon52 = new Pokemon(PokemonName.ABRA);
        Pokemon pokemon53 = new Pokemon(PokemonName.HYPNO);
        Pokemon pokemon54 = new Pokemon(PokemonName.MRMIME);
        Pokemon pokemon55 = new Pokemon(PokemonName.ALAKAZAM);
        pokemon51.setLevel(49);
        pokemon52.setLevel(50);
        pokemon53.setLevel(50);
        pokemon54.setLevel(52);
        pokemon55.setLevel(55);

        Pokemon pokemon61 = new Pokemon(PokemonName.PONYTA);
        Pokemon pokemon62 = new Pokemon(PokemonName.CHARMELEON);
        Pokemon pokemon63 = new Pokemon(PokemonName.FLAREON);
        Pokemon pokemon64 = new Pokemon(PokemonName.ARCANINE);
        Pokemon pokemon65 = new Pokemon(PokemonName.MAGMAR);
        Pokemon pokemon66 = new Pokemon(PokemonName.CHARIZARD);
        pokemon61.setLevel(54);
        pokemon62.setLevel(55);
        pokemon63.setLevel(58);
        pokemon64.setLevel(56);
        pokemon65.setLevel(57);
        pokemon66.setLevel(59);

        Pokemon pokemon71 = new Pokemon(PokemonName.SANDSLASH);
        Pokemon pokemon72 = new Pokemon(PokemonName.CUBONE);
        Pokemon pokemon73 = new Pokemon(PokemonName.MAROWAK);
        Pokemon pokemon74 = new Pokemon(PokemonName.GRAVELER);
        Pokemon pokemon75 = new Pokemon(PokemonName.DUGTRIO);
        Pokemon pokemon76 = new Pokemon(PokemonName.RHYDON);
        pokemon71.setLevel(62);
        pokemon72.setLevel(62);
        pokemon73.setLevel(64);
        pokemon74.setLevel(63);
        pokemon75.setLevel(66);
        pokemon76.setLevel(70);

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
        trainer0.setFirstName("Brock");
        trainer0.setLastName("Blindman");
        trainer0.setUserName("Brock");
        Date dob = new Calendar.Builder().setDate(1987, 24, 8).build().getTime();
        trainer0.setDateOfBirth(dob);

        Trainer trainer1 = new Trainer(pokemon11);
        trainer1.addPokemon(pokemon12);
        trainer1.addPokemon(pokemon13);
        trainer1.addPokemon(pokemon14);
        trainer1.addPokemon(pokemon15);
        trainer1.addPokemon(pokemon16);
        trainer1.setFirstName("Misty");
        trainer1.setLastName("Waterproof");
        trainer1.setUserName("MistygurlxD");
        dob = new Calendar.Builder().setDate(1995, 7, 7).build().getTime();
        trainer1.setDateOfBirth(dob);

        Trainer trainer2 = new Trainer(pokemon21);
        trainer2.addPokemon(pokemon22);
        trainer2.addPokemon(pokemon23);
        trainer2.addPokemon(pokemon24);
        trainer2.addPokemon(pokemon25);
        trainer2.setFirstName("Lucius");
        trainer2.setLastName("Surge");
        trainer2.setUserName("Headsh0tman");
        dob = new Calendar.Builder().setDate(1989, 20, 12).build().getTime();
        trainer2.setDateOfBirth(dob);

        Trainer trainer3 = new Trainer(pokemon31);
        trainer3.addPokemon(pokemon32);
        trainer3.addPokemon(pokemon33);
        trainer3.addPokemon(pokemon34);
        trainer3.addPokemon(pokemon35);
        trainer3.setFirstName("Erika");
        trainer3.setLastName("Nadilowska");
        trainer3.setUserName("Krowka227");
        dob = new Calendar.Builder().setDate(1994, 15, 2).build().getTime();
        trainer3.setDateOfBirth(dob);

        Trainer trainer4 = new Trainer(pokemon41);
        trainer4.addPokemon(pokemon42);
        trainer4.addPokemon(pokemon43);
        trainer4.addPokemon(pokemon44);
        trainer4.addPokemon(pokemon45);
        trainer4.addPokemon(pokemon46);
        trainer4.setFirstName("Janine");
        trainer4.setLastName("Poisoulous");
        trainer4.setUserName("MsPoIsON1997");
        dob = new Calendar.Builder().setDate(1997, 2, 7).build().getTime();
        trainer4.setDateOfBirth(dob);

        Trainer trainer5 = new Trainer(pokemon51);
        trainer5.addPokemon(pokemon52);
        trainer5.addPokemon(pokemon53);
        trainer5.addPokemon(pokemon54);
        trainer5.addPokemon(pokemon55);
        trainer5.setFirstName("Sabrina");
        trainer5.setLastName("McGonagall");
        trainer5.setUserName("Psyxox");
        dob = new Calendar.Builder().setDate(1991, 10, 1).build().getTime();
        trainer5.setDateOfBirth(dob);

        Trainer trainer6 = new Trainer(pokemon61);
        trainer6.addPokemon(pokemon62);
        trainer6.addPokemon(pokemon63);
        trainer6.addPokemon(pokemon64);
        trainer6.addPokemon(pokemon65);
        trainer6.addPokemon(pokemon66);
        trainer6.setFirstName("Blaine");
        trainer6.setLastName("Oldman");
        trainer6.setUserName("Quizman999");
        dob = new Calendar.Builder().setDate(1952, 27, 7).build().getTime();
        trainer6.setDateOfBirth(dob);

        Trainer trainer7 = new Trainer(pokemon71);
        trainer7.addPokemon(pokemon72);
        trainer7.addPokemon(pokemon73);
        trainer7.addPokemon(pokemon74);
        trainer7.addPokemon(pokemon75);
        trainer7.addPokemon(pokemon76);
        trainer7.setFirstName("Giovanni");
        trainer7.setLastName("Margherita");
        trainer7.setUserName("BadGuy3");
        dob = new Calendar.Builder().setDate(1988, 5, 10).build().getTime();
        trainer7.setDateOfBirth(dob);

        trainerDao.save(trainer0);
        trainerDao.save(trainer1);
        trainerDao.save(trainer2);
        trainerDao.save(trainer3);
        trainerDao.save(trainer4);
        trainerDao.save(trainer5);
        trainerDao.save(trainer6);
        trainerDao.save(trainer7);

        pokemon01.setTrainer(trainer0);
        pokemon02.setTrainer(trainer0);
        pokemon03.setTrainer(trainer0);
        pokemon04.setTrainer(trainer0);
        pokemon05.setTrainer(trainer0);
        pokemon11.setTrainer(trainer1);
        pokemon12.setTrainer(trainer1);
        pokemon13.setTrainer(trainer1);
        pokemon14.setTrainer(trainer1);
        pokemon15.setTrainer(trainer1);
        pokemon16.setTrainer(trainer1);
        pokemon21.setTrainer(trainer2);
        pokemon22.setTrainer(trainer2);
        pokemon23.setTrainer(trainer2);
        pokemon24.setTrainer(trainer2);
        pokemon25.setTrainer(trainer2);
        pokemon31.setTrainer(trainer3);
        pokemon32.setTrainer(trainer3);
        pokemon33.setTrainer(trainer3);
        pokemon34.setTrainer(trainer3);
        pokemon35.setTrainer(trainer3);
        pokemon41.setTrainer(trainer4);
        pokemon42.setTrainer(trainer4);
        pokemon43.setTrainer(trainer4);
        pokemon44.setTrainer(trainer4);
        pokemon45.setTrainer(trainer4);
        pokemon46.setTrainer(trainer4);
        pokemon51.setTrainer(trainer5);
        pokemon52.setTrainer(trainer5);
        pokemon53.setTrainer(trainer5);
        pokemon54.setTrainer(trainer5);
        pokemon55.setTrainer(trainer5);
        pokemon61.setTrainer(trainer6);
        pokemon62.setTrainer(trainer6);
        pokemon63.setTrainer(trainer6);
        pokemon64.setTrainer(trainer6);
        pokemon65.setTrainer(trainer6);
        pokemon66.setTrainer(trainer6);
        pokemon71.setTrainer(trainer7);
        pokemon72.setTrainer(trainer7);
        pokemon73.setTrainer(trainer7);
        pokemon74.setTrainer(trainer7);
        pokemon75.setTrainer(trainer7);
        pokemon76.setTrainer(trainer7);

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

        Gym gym0 = new Gym(trainer0);
        gym0.setCity("Pewter");
        gym0.setType(PokemonType.ROCK);

        Gym gym1 = new Gym(trainer1);
        gym1.setCity("Cerulean");
        gym1.setType(PokemonType.WATER);

        Gym gym2 = new Gym(trainer2);
        gym2.setCity("Vermillion");
        gym2.setType(PokemonType.ELECTRIC);

        Gym gym3 = new Gym(trainer3);
        gym3.setCity("Celadon");
        gym3.setType(PokemonType.GRASS);

        Gym gym4 = new Gym(trainer4);
        gym4.setCity("Fuchsia");
        gym4.setType(PokemonType.POISON);

        Gym gym5 = new Gym(trainer5);
        gym5.setCity("Saffron");
        gym5.setType(PokemonType.PSYCHIC);

        Gym gym6 = new Gym(trainer6);
        gym6.setCity("Cinnabar");
        gym6.setType(PokemonType.FIRE);

        Gym gym7 = new Gym(trainer7);
        gym7.setCity("Viridian");
        gym7.setType(PokemonType.GROUND);


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

    @Override
    public List<Gym> findAll() {
        return gymDao.findAll();
    }
}
