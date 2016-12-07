package com.kodemon.service.interfaces;

import java.util.Collection;

/**
 * Service for Dozer mappings
 */
public interface BeanMappingService {

    <T> Collection<T> mapCollectionTo(Collection<?> source, Class<T> mapToClass);
    <T> T mapTo(Object source, Class<T> mapToClass);
}
