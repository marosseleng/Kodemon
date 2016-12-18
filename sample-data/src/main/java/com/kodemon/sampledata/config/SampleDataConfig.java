package com.kodemon.sampledata.config;

import com.kodemon.sampledata.SampleDataLoadingFacade;
import com.kodemon.sampledata.SampleDataLoadingFacadeImpl;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.util.PasswordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Oliver Roch
 */

@Configuration
@Import(ServiceConfig.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class SampleDataConfig {
    final static Logger LOG = LoggerFactory.getLogger(SampleDataConfig.class);
    @Inject
    SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws PasswordStorage.CannotPerformOperationException {
        LOG.debug("loading sample data");
        sampleDataLoadingFacade.loadData();
    }
}
