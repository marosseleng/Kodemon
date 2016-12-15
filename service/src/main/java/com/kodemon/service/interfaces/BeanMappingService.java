package com.kodemon.service.interfaces;

import java.util.List;
import java.util.Set;

/**
 * Service for Dozer mappings
 */
public interface BeanMappingService {

    <T> List<T> mapListTo(List<?> source, Class<T> mapToClass);
    <T> T mapTo(Object source, Class<T> mapToClass);
}
