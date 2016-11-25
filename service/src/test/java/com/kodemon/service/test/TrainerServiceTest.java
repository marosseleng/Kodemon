package com.kodemon.service.test;

import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.entity.*;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.TrainerService;
import com.kodemon.service.util.Pair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the Trainer Service.
 *
 * @author Miso Romanek
 */

@ContextConfiguration(classes=ServiceConfig.class)
public class TrainerServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private TrainerDao trainerDao;

    @Inject
    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer1;
    private Trainer trainer2;

    private Pokemon abra;
    private Pokemon ditto;

    private Gym gym1;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepare() {
        abra = new Pokemon(PokemonName.ABRA);
        abra.setLevel(6);
        abra.setTrainer(trainer1);
        abra.setNickname("Mageman");

        trainer1 = new Trainer();
        trainer1.setFirstName("Bob");
        trainer1.setLastName("Ross");
        Date dob = new Calendar.Builder().setDate(1989, 7, 12).build().getTime();
        trainer1.setDateOfBirth(dob);
        trainer1.setUserName("Bobby");
        trainer1.setPwdHash("heslo1");
        trainer1.addPokemon(abra);

        ditto = new Pokemon(PokemonName.DITTO);
        ditto.setLevel(7);
        ditto.setNickname("Sliz");
        ditto.setTrainer(trainer2);

        trainer2 = new Trainer();
        trainer2.setFirstName("Chris");
        trainer2.setLastName("Angel");
        Date dob2 = new Calendar.Builder().setDate(1999, 8, 8).build().getTime();
        trainer2.setDateOfBirth(dob2);
        trainer2.setUserName("YoungWonder");
        trainer2.setPwdHash("heslo2");
        trainer2.addPokemon(ditto);

        gym1 = new Gym();
        gym1.setTrainer(trainer2);
        gym1.setCity("Gotham City");
        gym1.setType(PokemonType.NORMAL);
    }

    @Test
    public void findByUserNameTest() {
        when(trainerDao.findByUserName("Bobby")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByUserName("Bobby");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByUserName("Bobby");
    }

    @Test
    public void findByUserNameLikeTest() {
        when(trainerDao.findByUserNameLike("Bob-by")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByUserNameLike("Bob-by");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByUserNameLike("Bob-by");
    }

    @Test
    public void findByUserNameStartingWithTest() {
        when(trainerDao.findByUserNameStartingWith("Bo")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByUserNameStartingWith("Bo");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByUserNameStartingWith("Bo");
    }

    @Test
    public void findByUserNameEndingWithTest() {
        when(trainerDao.findByUserNameEndingWith("by")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByUserNameEndingWith("by");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByUserNameEndingWith("by");
    }

    @Test
    public void findByUserNameContainingTest() {
        when(trainerDao.findByUserNameContaining("obb")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByUserNameContaining("obb");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByUserNameContaining("obb");
    }

    @Test
    public void findByFirstNameTest() {
        when(trainerDao.findByFirstName("Bob")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByFirstName("Bob");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByFirstName("Bob");
    }

    @Test
    public void findByLastNameTest() {
        when(trainerDao.findByLastName("Ross")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByLastName("Ross");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByLastName("Ross");
    }

    @Test
    public void findByFirstNameAndLastNameTest() {
        when(trainerDao.findByFirstNameAndLastName("Bob", "Ross")).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByFirstNameAndLastName("Bob", "Ross");
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByFirstNameAndLastName("Bob", "Ross");
    }

    @Test
    public void findByDateOfBirthTest() {
        Date dobx = new Calendar.Builder().setDate(1989, 7, 12).build().getTime();
        when(trainerDao.findByDateOfBirth(dobx)).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByDateOfBirth(dobx);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByDateOfBirth(dobx);
    }

    @Test
    public void findByDateOfBirthBetweenTest() {
        Date dob1 = new Calendar.Builder().setDate(1980, 3, 30).build().getTime();
        Date dob2 = new Calendar.Builder().setDate(1990, 4, 3).build().getTime();
        when(trainerDao.findByDateOfBirthBetween(dob1, dob2)).thenReturn(Collections.singletonList(trainer1));
        List<Trainer> result = trainerService.findByDateOfBirthBetween(dob1, dob2);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(trainer1));
        verify(trainerDao).findByDateOfBirthBetween(dob1, dob2);
    }

    @Test
    public void findAllTest() {
        List<Trainer> correctResult = new ArrayList<>();
        correctResult.add(trainer1);
        correctResult.add(trainer2);
        when(trainerDao.findAll()).thenReturn(correctResult);
        List<Trainer> result = trainerService.findAll();
        assertThat(result.size(), is(2));
        assertThat(result, is(correctResult));
        verify(trainerDao).findAll();
    }

    @Test
    public void saveCorrectTest() {
        when(trainerDao.save(trainer1)).thenReturn(trainer1);
        trainerService.save(trainer1);
        verify(trainerDao).save(trainer1);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveNullTrainerTest() {
        Trainer trainerx = null;
        when(trainerDao.save(trainerx)).thenThrow(NullPointerException.class);
        trainerService.save(trainerx);
        verify(trainerDao).save(trainerx);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveTrainerWithNullFirstnameTest() {
        Trainer trainerx = new Trainer();
        trainerx.setFirstName(null);
        trainerx.setLastName("Smith");
        Date dob = new Calendar.Builder().setDate(1997, 7, 2).build().getTime();
        trainerx.setDateOfBirth(dob);
        trainerx.setUserName("Smithy");
        when(trainerDao.save(trainerx)).thenThrow(NullPointerException.class);
        trainerService.save(trainerx);
        verify(trainerDao).save(trainerx);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveTrainerWithNullLastnameTest() {
        Trainer trainerx = new Trainer();
        trainerx.setFirstName("Peter");
        trainerx.setLastName(null);
        Date dob = new Calendar.Builder().setDate(1997, 7, 2).build().getTime();
        trainerx.setDateOfBirth(dob);
        trainerx.setUserName("Smithy");
        when(trainerDao.save(trainerx)).thenThrow(NullPointerException.class);
        trainerService.save(trainerx);
        verify(trainerDao).save(trainerx);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveTrainerWithNullDateOfBirthTest() {
        Trainer trainerx = new Trainer();
        trainerx.setFirstName("Peter");
        trainerx.setLastName("Smith");
        trainerx.setDateOfBirth(null);
        trainerx.setUserName("Smithy");
        when(trainerDao.save(trainerx)).thenThrow(NullPointerException.class);
        trainerService.save(trainerx);
        verify(trainerDao).save(trainerx);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveTrainerWithNullFirstAndLastNameTest() {
        Trainer trainerx = new Trainer();
        trainerx.setFirstName(null);
        trainerx.setLastName(null);
        Date dob = new Calendar.Builder().setDate(1997, 7, 2).build().getTime();
        trainerx.setDateOfBirth(dob);
        trainerx.setUserName("Smithy");
        when(trainerDao.save(trainerx)).thenThrow(NullPointerException.class);
        trainerService.save(trainerx);
        verify(trainerDao).save(trainerx);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveTrainerWithNullUserNameTest() {
        Trainer trainerx = new Trainer();
        trainerx.setFirstName("Peter");
        trainerx.setLastName("Smith");
        Date dob = new Calendar.Builder().setDate(1997, 7, 2).build().getTime();
        trainerx.setDateOfBirth(dob);
        trainerx.setUserName(null);
        when(trainerDao.save(trainerx)).thenThrow(NullPointerException.class);
        trainerService.save(trainerx);
        verify(trainerDao).save(trainerx);
    }

    public void deleteCorrectTest() {
        Trainer trainerx = new Trainer();
        trainerx.setFirstName("Peter");
        trainerx.setLastName("Smith");
        Date dob = new Calendar.Builder().setDate(1977, 7, 2).build().getTime();
        trainerx.setDateOfBirth(dob);
        trainerx.setUserName("Smithy");
        trainerService.save(trainerx);
        doNothing().when(trainerDao).delete(trainerx);
        trainerService.delete(trainerx);
        verify(trainerDao).delete(trainerx);
    }

/* Additional tests that could be added in the future
    @Test
    public void registerTest() {
        Trainer trainer3 = new Trainer();
        trainer3.setFirstName("Gustav");
        trainer3.setLastName("Martinson");
        Date dob = new Calendar.Builder().setDate(1944, 10, 11).build().getTime();
        trainer3.setDateOfBirth(dob);
        trainer3.setUserName("Gusto");
        when(trainerService.register(trainer3, "topsecret4")).thenReturn(true);
        Boolean result = trainerService.register(trainer3, "topsecret4");
        assertThat(result, is(true));
    }

    @Test
    public void loginTest() {
        when(trainerService.login("Bobby", "heslo1")).thenReturn(true);
        Boolean result = trainerService.login("Bobby", "heslo1");
        assertThat(result, is(true));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void addPokemonTest() {
        Pokemon gloom = new Pokemon(PokemonName.GLOOM);
        gloom.setLevel(9);
        gloom.setNickname("Sadboy");
        gloom.setTrainer(trainer1);
        trainer1.addPokemon(gloom);
        assertThat(trainer1.getPokemons(), contains(gloom));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void addBadgeTest() {
        Badge badge = new Badge();
        badge.setName("Super badge");
        badge.setGym(gym1);
        badge.setTrainer(trainer1);
        trainer1.addBadge(badge);
        assertThat(trainer1.getBadges(), contains(badge));
    }*/
}
