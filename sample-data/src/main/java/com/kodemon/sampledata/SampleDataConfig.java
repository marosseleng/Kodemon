package com.kodemon.sampledata;

import com.kodemon.service.config.ServiceConfig;
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
    @Inject
    SampleDataLoadingFacade sampleDataLoadingFacade;

    final static Logger LOG = LoggerFactory.getLogger(SampleDataConfig.class);

    @PostConstruct
    public void dataLoading() {
        LOG.debug("loading sample data");
        sampleDataLoadingFacade.loadData();
    }
}
