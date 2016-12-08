package com.kodemon.service.implementations;

import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.util.OrikaMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of BeanMappingService
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    private OrikaMapper mapper;

    @Inject
    public BeanMappingServiceImpl(OrikaMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> Collection<T> mapCollectionTo(Collection<?> source, Class<T> mapToClass) {
        if (mapToClass == null) {
            return Collections.emptyList();
        }
        return mapper.mapAsList(source, mapToClass);
    }

    @Override
    public <T> T mapTo(Object source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        return mapper.map(source, targetClass);
    }
}
