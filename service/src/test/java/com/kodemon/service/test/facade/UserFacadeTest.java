package com.kodemon.service.test.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.facade.UserFacadeImpl;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.TrainerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for User Facade
 *
 * @author Matej Poklemba
 */
@ContextConfiguration(classes=ServiceConfig.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    private UserFacade userFacade;
    private BeanMappingService beanMappingService;
    private TrainerService trainerService;

    private UserDTO user, user2;
    private Trainer trainer, trainer2;

    /*@BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }*/

    @BeforeMethod
    public void prepare() {
        beanMappingService = mock(BeanMappingService.class);
        trainerService = mock(TrainerService.class);
        userFacade = new UserFacadeImpl(beanMappingService, trainerService);

        trainer = new Trainer();
        trainer.setUserName("brock1999");
        trainer.setFirstName("Brock");
        trainer.setLastName("Brockowski");
        Date born = new Calendar.Builder().setDate(1999, 5, 5).build().getTime();
        trainer.setDateOfBirth(born);

        trainer2 = new Trainer();
        trainer2.setUserName("blin5");
        trainer2.setFirstName("Adam");
        trainer2.setLastName("Blin");
        born = new Calendar.Builder().setDate(1995, 4, 8).build().getTime();
        trainer2.setDateOfBirth(born);

        user = new UserDTO();
        user.setUserName("brock1999");
        user.setFirstName("Brock");
        user.setLastName("Brockowski");
        born = new Calendar.Builder().setDate(1999, 5, 5).build().getTime();
        user.setDateOfBirth(born);

        user2 = new UserDTO();
        user2.setUserName("blin5");
        user2.setFirstName("Adam");
        user2.setLastName("Blin");
        born = new Calendar.Builder().setDate(1995, 4, 8).build().getTime();
        user2.setDateOfBirth(born);
    }

    @Test
    public void registerTest() {
        when(trainerService.register(trainer, "password")).thenReturn(true);
        boolean result = userFacade.register(user, "password");
        assertThat(result, is(true));
    }

    @Test
    public void loginTest() {
        when(trainerService.register(trainer, "password")).thenReturn(true);
        userFacade.register(user, "password");

        UserAuthDTO userAuth = new UserAuthDTO();
        userAuth.setUserName(trainer.getUserName());
        userAuth.setPwdHash(trainer.getPwdHash());

        when(trainerService.login(trainer.getUserName(), "password")).thenReturn(true);
        boolean result = userFacade.login(userAuth);
        assertThat(result, is(true));
    }

    @Test
     public void listAllUsers() {
        List<Trainer> allTrainersE = new ArrayList<>();
        allTrainersE.add(trainer);
        allTrainersE.add(trainer2);
        List<UserDTO> allTrainers = new ArrayList<>();
        allTrainers.add(user);
        allTrainers.add(user2);
        when(trainerService.findAll()).thenReturn(allTrainersE);
        when(beanMappingService.mapTo(allTrainersE, UserDTO.class)).thenReturn(allTrainers);
        List<UserDTO> result = userFacade.findAllUsers();
        assertThat(result.size(), is(2));
        }
}
