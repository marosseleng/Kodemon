package com.kodemon.sampledata;

import com.kodemon.service.util.PasswordStorage;

/**
 * @author Oliver Roch
 */
public interface SampleDataLoadingFacade {
    void loadData() throws PasswordStorage.CannotPerformOperationException;
}
