package com.kodemon.service.test.facade;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.facade.GymFacadeImpl;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.GymService;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for Gym Facade
 *
 * @author Michal Romanek
 */
@ContextConfiguration(classes=ServiceConfig.class)
public class GymFacadeTest extends AbstractTestNGSpringContextTests {

    private GymFacade gymFacade;
    private BeanMappingService beanMappingService;
    private GymService gymService;

    private Gym gym1;
    private Gym gym2;
    private GymDTO gymdto1;
    private GymDTO gymdto2;

    @BeforeMethod
    public void prepare() {
        beanMappingService = mock(BeanMappingService.class);
        gymService = mock(GymService.class);
        gymFacade = new GymFacadeImpl(beanMappingService, gymService);
        UserDTO user1, user2;
        Trainer trainer1, trainer2;

        trainer1 = new Trainer();
        trainer1.setUserName("Tommy");
        trainer1.setFirstName("Tom");
        trainer1.setLastName("Lee");
        Date born = new Calendar.Builder().setDate(1996, 2, 7).build().getTime();
        trainer1.setDateOfBirth(born);

        trainer2 = new Trainer();
        trainer2.setUserName("Timmy");
        trainer2.setFirstName("Tim");
        trainer2.setLastName("Chan");
        born = new Calendar.Builder().setDate(1991, 11, 12).build().getTime();
        trainer2.setDateOfBirth(born);

        user1 = new UserDTO();
        user1.setUserName("Tommy");
        user1.setFirstName("Tom");
        user1.setLastName("Lee");
        born = new Calendar.Builder().setDate(1996, 2, 7).build().getTime();
        user1.setDateOfBirth(born);

        user2 = new UserDTO();
        user2.setUserName("Timmy");
        user2.setFirstName("Tim");
        user2.setLastName("Chan");
        born = new Calendar.Builder().setDate(1991, 11, 12).build().getTime();
        user2.setDateOfBirth(born);

        gym1 = new Gym();
        gym1.setType(PokemonType.BUG);
        gym1.setCity("Sydney");
        gym1.setTrainer(trainer1);

        gym2 = new Gym();
        gym2.setType(PokemonType.GHOST);
        gym2.setCity("Kansas City");
        gym2.setTrainer(trainer2);

        gymdto1 = new GymDTO();
        gymdto1.setType(PokemonType.BUG);
        gymdto1.setCity("Sydney");
        gymdto1.setTrainer(user1);

        gymdto2 = new GymDTO();
        gymdto2.setType(PokemonType.GHOST);
        gymdto2.setCity("Kansas City");
        gymdto2.setTrainer(user2);
    }

    @Test
    public void findAllTest() {
        List<Gym> allGyms = new ArrayList<>();
        allGyms.add(gym1);
        allGyms.add(gym2);
        List<GymDTO> allGymDTOs = new ArrayList<>();
        allGymDTOs.add(gymdto1);
        allGymDTOs.add(gymdto2);
        when(gymService.findAll()).thenReturn(allGyms);
        when(beanMappingService.mapListTo(gymService.findAll(), GymDTO.class)).thenReturn(allGymDTOs);
        Collection<GymDTO> result = gymFacade.findAll();
        assertThat(result.size(), is(2));
        assertThat(result.contains(gymdto1) && result.contains(gymdto2), is(true));
        }

    @AfterMethod
    void resetMocks() {
        Mockito.reset(beanMappingService, gymService);
    }
}
