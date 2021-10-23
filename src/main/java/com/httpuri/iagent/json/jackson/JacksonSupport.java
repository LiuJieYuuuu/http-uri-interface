package com.httpuri.iagent.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.httpuri.iagent.exception.JsonException;
import com.httpuri.iagent.json.JSONSupport;

import java.util.List;

/**
 * jackson adapter json framework
 */
public class JacksonSupport implements JSONSupport {

    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public JacksonSupport () throws Exception {
        super();
        Class<?> aClass = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
        objectMapper = (ObjectMapper) aClass.newInstance();
    }

    @Override
    public <T> List<T> getJSONArray(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, new TypeReference<List> () {});
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T getJSONObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
}
