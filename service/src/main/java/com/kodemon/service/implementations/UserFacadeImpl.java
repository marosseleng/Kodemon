package com.kodemon.service.implementations;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.TrainerService;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * User Facade Implementation
 *
 * Registers and logins users, finds user by userName and finds all users
 *
 * @author Matej Poklemba
 */
public class UserFacadeImpl implements UserFacade {
    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private TrainerService trainerService;

    @Override
    public boolean register(UserDTO user, String pwdHash) {
        return trainerService.register(beanMappingService.mapTo(user, Trainer.class), pwdHash);
    }

    @Override
    public boolean login(UserAuthDTO auth) {
        return trainerService.login(auth.getUserName(), auth.getPwdHash());
    }

    @Override
    public List<UserDTO> findUserByUserName(String userName) {
        return beanMappingService.mapTo(trainerService.findByUserName(userName), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return beanMappingService.mapTo(trainerService.findAll(), UserDTO.class);
    }
}
