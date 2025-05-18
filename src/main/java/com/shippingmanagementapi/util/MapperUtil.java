package com.shippingmanagementapi.util;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@Slf4j
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convert(Object objectToBeConverted, T convertedObject) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        try {
            return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());

        } catch (Exception e){
            log.info("Error on converting objects");
            throw new IllegalArgumentException("Not identified fields on object body");
        }
    }

}
