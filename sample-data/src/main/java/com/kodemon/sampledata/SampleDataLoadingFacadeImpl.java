package com.kodemon.sampledata;

import com.kodemon.service.interfaces.GymService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Oliver Roch
 */

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    @Inject
    private GymService gymService;

    @Override
    public void loadData() {
        gymService.initializeGyms();
    }
}
