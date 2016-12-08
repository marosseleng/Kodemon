package com.kodemon.service.implementations;

import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.util.OrikaMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        return mapper.mapAsList(objects, mapToClass);
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return mapper.map(u, mapToClass);
    }
}
