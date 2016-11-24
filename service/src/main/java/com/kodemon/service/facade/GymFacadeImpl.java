package com.kodemon.service.facade;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.GymService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.util.List;

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
    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    private GymService gymService;

    @Override
    public List<GymDTO> findAll() {
        return beanMappingService.mapTo(gymService.findAll(), GymDTO.class);
    }

}
