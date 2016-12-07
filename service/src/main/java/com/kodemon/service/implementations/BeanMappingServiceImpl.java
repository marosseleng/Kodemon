package com.kodemon.service.implementations;

import com.kodemon.service.interfaces.BeanMappingService;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of BeanMappingService
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    private Mapper mapper;

    @Inject
    public BeanMappingServiceImpl(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> Collection<T> mapCollectionTo(Collection<?> source, Class<T> targetClass) {
        if (targetClass == null) {
            return Collections.emptyList();
        }
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : source) {
            mappedCollection.add(mapper.map(object, targetClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        return mapper.map(source, targetClass);
    }
}
