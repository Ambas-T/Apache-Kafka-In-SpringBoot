package com.example.orderservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil<T> {
    ObjectMapper mapper = new ObjectMapper();

    public T stringToObject(String value, Class<T> dto) throws JsonProcessingException {
        return mapper.readValue(value, dto);
    }

    public String objectToString(T dto) throws JsonProcessingException {
        return mapper.writeValueAsString(dto);
    }
}
