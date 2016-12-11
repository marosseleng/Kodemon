package com.kodemon.service.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

/**
 * User Facade Implementation
 *
 * Registers and logins users, finds user by userName and finds all users
 *
 * @author Matej Poklemba
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private static final Logger LOG = LoggerFactory.getLogger(UserFacadeImpl.class);

    private BeanMappingService beanMappingService;
    private TrainerService trainerService;

    @Inject
    public UserFacadeImpl(
            BeanMappingService beanMappingService,
            TrainerService trainerService) {
        this.beanMappingService = beanMappingService;
        this.trainerService = trainerService;
    }

    @Override
    public boolean register(UserDTO user, String pwdHash) {
        LOG.debug("Registering user with userName {}.", user.getUserName());
        return trainerService.register(beanMappingService.mapTo(user, Trainer.class), pwdHash);
    }

    @Override
    public boolean login(UserAuthDTO auth) {
        LOG.debug("Logging in user with userName {}", auth.getUserName());
        return trainerService.login(auth.getUserName(), auth.getPassword());
    }

    @Override
    public Collection<UserDTO> findUserByUserName(String userName) {
        return beanMappingService.mapCollectionTo(trainerService.findByUserName(userName), UserDTO.class);
    }

    @Override
    public Collection<UserDTO> findAllUsers() {
        return beanMappingService.mapCollectionTo(trainerService.findAll(), UserDTO.class);
    }
}
