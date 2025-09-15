package com.epam.gym.storage;

import com.epam.gym.utils.FilesUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class JsonDataStorage<K, T> extends MapDataStorage<K, T> {
    private String externalLocation;
    private boolean saveUpdates;

    public JsonDataStorage(Class<K> keyClazz, Class<T> valueClazz) {
        super(keyClazz, valueClazz);
    }

    public void setExternalStorage(String location, boolean saveUpdates) {
        this.externalLocation = location;
        this.saveUpdates = saveUpdates;
    }

    @PostConstruct
    public void init() throws IOException {
        if (externalLocation == null || externalLocation.isEmpty()) {
            return;
        }

        File sourceFile = FilesUtils.getDataSource(externalLocation, valueClazz);
        if (!sourceFile.exists()) {
            return;
        }

        if (sourceFile.isDirectory()) {
            throw new IOException("File by path " + sourceFile.getPath() + " is directory");
        }

        ObjectMapper mapper = new ObjectMapper();
        String content = Files.readString(sourceFile.toPath());

        MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz);
        entities.putAll(mapper.readValue(content, mapType));
    }

    @PreDestroy
    public void destroy() throws IOException {
        if (!saveUpdates || externalLocation == null || externalLocation.isEmpty()) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(entities);

        File destFile = FilesUtils.getDataSource(externalLocation, valueClazz);
        Files.writeString(destFile.toPath(), content);
    }
}
