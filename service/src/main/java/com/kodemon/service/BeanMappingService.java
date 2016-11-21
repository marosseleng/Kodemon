package com.kodemon.service;

import java.util.Collection;
import java.util.List;

/**
 * Service for Dozer mappings
 */
public interface BeanMappingService {

    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public  <T> T mapTo(Object u, Class<T> mapToClass);
//    public /*Mapper getMapper();*/
}
