package com.kodemon.rest.config;

import com.kodemon.rest.resources.UserResource;
import com.kodemon.sampledata.config.SampleDataConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring Annotations configuration
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Configuration
@Import(SampleDataConfig.class)
@ComponentScan(basePackageClasses = UserResource.class)
public class SpringAppConfiguration {
}
