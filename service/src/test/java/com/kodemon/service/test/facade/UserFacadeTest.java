package com.kodemon.service.test.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.facade.UserFacadeImpl;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.TrainerService;
import com.kodemon.service.util.PasswordStorage;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
    public void registerTest() throws PasswordStorage.CannotPerformOperationException {
        when(beanMappingService.mapTo(user, Trainer.class)).thenReturn(trainer);
        when(trainerService.register(trainer, "password")).thenReturn(trainer);
        assertThat(userFacade.register(user, "password"), is(equalTo(user)));
    }

    @Test
    public void loginTest() throws PasswordStorage.CannotPerformOperationException {
        when(beanMappingService.mapTo(user, Trainer.class)).thenReturn(trainer);
        when(trainerService.register(trainer, "password")).thenReturn(trainer);
        assertThat(userFacade.register(user, "password"), is(equalTo(user)));

        UserAuthDTO userAuth = new UserAuthDTO();
        userAuth.setUserName(trainer.getUserName());
        userAuth.setPassword(trainer.getPwdHash());

        when(trainerService.login(userAuth.getUserName(), userAuth.getPassword())).thenReturn(true);
        boolean result = userFacade.login(userAuth);
        assertThat(result, is(true));
    }

    @Test
    public void findUserByUserNameTest() {
        List<Trainer> trainerList = new ArrayList<>();
        trainerList.add(trainer);
        List<UserDTO> userList = new ArrayList<>();
        userList.add(user);
        when(trainerService.findByUserName(trainer.getUserName())).thenReturn(trainerList);
        when(beanMappingService.mapCollectionTo(trainerList, UserDTO.class)).thenReturn(userList);
        Collection<UserDTO> result = userFacade.findUserByUserName(user.getUserName());
        assertThat(result.size(), is(1));
        assertThat(result, is(equalTo(userList)));
    }

    @Test
    public void findAllUsersTest() {
        List<Trainer> allTrainers = new ArrayList<>();
        allTrainers.add(trainer);
        allTrainers.add(trainer2);
        List<UserDTO> allUsers = new ArrayList<>();
        allUsers.add(user);
        allUsers.add(user2);
        when(trainerService.findAll()).thenReturn(allTrainers);
        when(beanMappingService.mapCollectionTo(allTrainers, UserDTO.class)).thenReturn(allUsers);
        Collection<UserDTO> result = userFacade.findAllUsers();
        assertThat(result.size(), is(2));
        assertThat(result.contains(user) && result.contains(user2), is(true));
        }

    @AfterMethod
    void resetMocks() {
        Mockito.reset(beanMappingService);
        Mockito.reset(trainerService);
    }
}
