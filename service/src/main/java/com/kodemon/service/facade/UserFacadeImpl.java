package com.kodemon.service.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.TrainerService;
import com.kodemon.service.util.PasswordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

/**
 * User Facade Implementation
 * <p>
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
    public UserDTO register(UserDTO user, String pwdHash) {
        LOG.debug("Registering user with userName {}.", user.getUserName());
        try {
            Trainer trainer = trainerService.register(beanMappingService.mapTo(user, Trainer.class), pwdHash);
            return beanMappingService.mapTo(trainer, UserDTO.class);
        } catch (PasswordStorage.CannotPerformOperationException e) {
            LOG.error("Error while registering user", e);
            return null;
        }
    }

    @Override
    public boolean login(UserAuthDTO auth) {
        LOG.debug("Logging in user with userName {}", auth.getUserName());
        return trainerService.login(auth.getUserName(), auth.getPassword());
    }

    @Override
    public Collection<UserDTO> findUserByUserName(String userName) {
        return beanMappingService.mapListTo(trainerService.findByUserNameIgnoreCaseContaining(userName), UserDTO.class);
    }

    @Override
    public Collection<UserDTO> findAllUsers() {
        return beanMappingService.mapListTo(trainerService.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO findOneUser(Long id) {
        return beanMappingService.mapTo(trainerService.findById(id), UserDTO.class);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        Trainer toUpdate = beanMappingService.mapTo(userDTO, Trainer.class);
        return beanMappingService.mapTo(trainerService.update(id, toUpdate), UserDTO.class);
    }

    @Override
    public void delete(Long id) {
        trainerService.delete(id);
    }

    @Override
    public void setBlocked(Long id, boolean blocked) {
        trainerService.setBlocked(id, blocked);
    }
}
