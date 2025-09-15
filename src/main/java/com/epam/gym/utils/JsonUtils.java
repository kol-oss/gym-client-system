package com.epam.gym.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Map;

public final class JsonUtils {
    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    public static String toJson(Object obj) throws IOException {
        ObjectMapper mapper = getMapper();

        return mapper.writeValueAsString(obj);
    }

    public static <K, T> Map<K, T> fromJson(String json, Class<K> keyClazz, Class<T> valueClazz) throws IOException {
        ObjectMapper mapper = getMapper();

        MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz);
        return mapper.readValue(json, mapType);
    }
}
