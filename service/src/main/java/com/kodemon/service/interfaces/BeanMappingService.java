package com.kodemon.service.interfaces;

import org.dozer.Mapper;

import java.util.Collection;

/**
 * Service for Dozer mappings
 */
public interface BeanMappingService {

    <T> Collection<T> mapCollectionTo(Collection<?> objects, Class<T> mapToClass);
    <T> T mapTo(Object u, Class<T> mapToClass);
}
