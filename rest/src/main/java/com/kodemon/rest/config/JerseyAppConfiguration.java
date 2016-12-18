package com.kodemon.rest.config;

import com.kodemon.rest.resources.UserResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey application configuration
 * <p>
 * After hours of jersey complaining that it cannot find the applicationContext.xml, the solution was found:
 * https://github.com/peeskillet/underdog-jersey-spring-example
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@ApplicationPath("rest")
public class JerseyAppConfiguration extends ResourceConfig {
    private static final Logger LOG = LoggerFactory.getLogger(JerseyAppConfiguration.class);

    public JerseyAppConfiguration() {
        registerClasses(
                JacksonFeature.class,
                UserResource.class,
                ObjectMapperProvider.class);
    }
}
