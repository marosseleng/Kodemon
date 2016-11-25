package com.kodemon.api.facade;

import com.kodemon.api.dto.GymDTO;

import java.util.List;

/**
 * Facade providing access to the gym administration
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a> and Miso Romanek
 */
public interface GymFacade {
    /**
     * Returns all {@link com.kodemon.persistence.entity.Gym}s that are stored in the system
     *
     * @return a {@link List} of {@link com.kodemon.persistence.entity.Gym}s
     */
    List<GymDTO> findAll();
}
