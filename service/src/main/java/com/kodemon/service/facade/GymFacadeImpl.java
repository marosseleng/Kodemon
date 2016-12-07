package com.kodemon.service.facade;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.GymService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Gym Facade Implementation
 *
 * Registers and logins users, finds user by userName and finds all users
 *
 * @author Miso Romanek
 */

@Service
@Transactional
public class GymFacadeImpl implements GymFacade {

    private BeanMappingService beanMappingService;
    private GymService gymService;

    @Inject
    public GymFacadeImpl(
            BeanMappingService beanMappingService,
            GymService gymService) {
        this.beanMappingService = beanMappingService;
        this.gymService = gymService;
    }

    @Override
    public Collection<GymDTO> findAll() {
        return beanMappingService.mapCollectionTo(gymService.findAll(), GymDTO.class);
    }
}