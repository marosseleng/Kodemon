package com.kodemon.service.test.facade;

import com.kodemon.api.config.ApiConfig;
import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Oliver Roch
 */

@ContextConfiguration(classes=ApiConfig.class)
public class FightFacadeTest extends AbstractTestNGSpringContextTests {
    @Inject
    private FightFacade fightFacade;

    private UserDTO challenger;
    private UserDTO defender;
    private GymDTO targetGym;

    private PokemonDTO pikachu;
    private PokemonDTO onix;

    @BeforeMethod
    public void prepare() {
        prepareTrainers();
        prepareGym();
    }

    @Test
    public void fightForBadgeTest() {
        fightFacade.fightForBadge(challenger, targetGym);
        assertThat(challenger.getBadges(), is(Collections.emptySet()));
    }

    private void prepareTrainers() {
        pikachu = new PokemonDTO();
        pikachu.setName(PokemonName.PIKACHU);
        pikachu.setLevel(4);
        pikachu.setTrainer(challenger);
        pikachu.setNickname("Yellow mouse");

        challenger = new UserDTO();
        challenger.setFirstName("Ash");
        challenger.setLastName("Ketchum");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        challenger.setDateOfBirth(dob);
        challenger.setUserName("Ash123");
        challenger.addPokemon(pikachu);

        onix = new PokemonDTO();
        onix.setName(PokemonName.ONIX);
        onix.setLevel(5);
        onix.setNickname("The Rock");
        onix.setTrainer(defender);

        defender = new UserDTO();
        defender.setFirstName("Brock");
        defender.setLastName("Takechi");
        Date dob2 = new Calendar.Builder().setDate(1984, 2, 3).build().getTime();
        defender.setDateOfBirth(dob2);
        defender.setUserName("Brocky123");
        defender.addPokemon(onix);
    }

    private void prepareGym() {
        targetGym = new GymDTO();
        targetGym.setTrainer(defender);
        targetGym.setCity("Violet city");
        targetGym.setType(PokemonType.GROUND);
    }
}
