package com.kodemon.rest.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Created by mseleng on 12/11/16.
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ObjectMapperProvider() {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        return MAPPER;
    }
}
