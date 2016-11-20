package com.kodemon.api.facade;

import com.kodemon.api.dto.GymDTO;

import java.util.List;

/**
 * Created by mseleng on 11/19/16.
 */
public interface GymFacade {
    List<GymDTO> listAllGyms();
}
