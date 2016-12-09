package com.kodemon.sampledata;

import com.kodemon.service.interfaces.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Roch
 */

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    @Autowired
    private GymService gymService;

    @Override
    public void loadData() {
        gymService.initializeGyms();
    }
}
