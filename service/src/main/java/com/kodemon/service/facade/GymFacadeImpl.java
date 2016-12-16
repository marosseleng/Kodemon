package com.kodemon.service.facade;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.GymService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Gym Facade Implementation
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
        return beanMappingService.mapListTo(gymService.findAll(), GymDTO.class);
    }

    // Veeeery ugly workaround for #205.
    // When only delegating method to the layer below, leader of the returned gym had (number_of_fights * 5) pokemons.
    // Now it should be correct
    @Override
    public GymDTO findGymById(Long id) {
        Collection<Gym> all = gymService.findAll();
        for (Gym g : all) {
            if (g.getId().equals(id)) {
                return beanMappingService.mapTo(g, GymDTO.class);
            }
        }
        return null;
    }
}