package com.kodemon.service.test.facade;

import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.PokemonFacade;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.facade.PokemonFacadeImpl;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.PokemonService;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

/**
 * Test class for Pokemon Facade
 *
 * @author Matej Poklemba
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class PokemonFacadeTest extends AbstractTestNGSpringContextTests {

    private PokemonFacade pokemonFacade;
    private BeanMappingService beanMappingService;
    private PokemonService pokemonService;

    private Pokemon pokemon;
    private PokemonDTO pokemonDto;
    private Trainer trainer;
    private UserDTO user;

    @BeforeMethod
    public void prepare() {
        beanMappingService = mock(BeanMappingService.class);
        pokemonService = mock(PokemonService.class);
        pokemonFacade = new PokemonFacadeImpl(beanMappingService, pokemonService);

        trainer = new Trainer();
        trainer.setUserName("Tommy");
        trainer.setFirstName("Tom");
        trainer.setLastName("Lee");
        Date born = new Calendar.Builder().setDate(1996, 2, 7).build().getTime();
        trainer.setDateOfBirth(born);

        user = new UserDTO();
        user.setUserName("Tommy");
        user.setFirstName("Tom");
        user.setLastName("Lee");
        born = new Calendar.Builder().setDate(1996, 2, 7).build().getTime();
        user.setDateOfBirth(born);

        pokemon = new Pokemon();
        pokemon.setName(PokemonName.PIKACHU);
        pokemon.setLevel(5);
        pokemon.setTrainer(trainer);

        pokemonDto = new PokemonDTO();
        pokemonDto.setName(PokemonName.PIKACHU);
        pokemonDto.setLevel(5);
        pokemonDto.setTrainer(user);
    }

    @Test
    public void renamePokemonTest() {
        when(beanMappingService.mapTo(pokemonDto, Pokemon.class)).thenReturn(pokemon);
        assertThat(pokemon.getNickname(), is(nullValue()));
        pokemonFacade.renamePokemon(pokemonDto, "Krysa");
        verify(pokemonService).renamePokemon(pokemon, "Krysa");
    }

    @AfterMethod
    void resetMocks() {
        Mockito.reset(beanMappingService, pokemonService);
    }
}
