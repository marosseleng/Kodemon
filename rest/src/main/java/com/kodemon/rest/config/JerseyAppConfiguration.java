package com.kodemon.rest.config;

import com.kodemon.rest.resources.UserResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

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
    public JerseyAppConfiguration() {
        registerClasses(
                JacksonFeature.class,
                UserResource.class,
                ObjectMapperProvider.class);
    }
}
