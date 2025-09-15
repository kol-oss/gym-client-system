package com.epam.gym.storage;

import com.epam.gym.utils.FilesUtils;
import com.epam.gym.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class FileDataStorage<K, T> extends MapDataStorage<K, T> {
    private final String location;
    private final boolean saveUpdates;

    public FileDataStorage(Class<K> keyClazz, Class<T> valueClazz, String location, boolean saveUpdates) {
        super(keyClazz, valueClazz);

        this.location = location;
        this.saveUpdates = saveUpdates;
    }

    @PostConstruct
    public void init() throws IOException {
        if (location == null || location.isEmpty()) {
            return;
        }

        File sourceFile = FilesUtils.getDataSource(location, valueClazz);
        if (!sourceFile.exists()) {
            return;
        }

        if (sourceFile.isDirectory()) {
            throw new IOException("File by path " + sourceFile.getPath() + " is directory");
        }

        String content = Files.readString(sourceFile.toPath());
        Map<K, T> parsed = JsonUtils.fromJson(content, keyClazz, valueClazz);

        entities.putAll(parsed);
    }

    @PreDestroy
    public void destroy() throws IOException {
        if (!saveUpdates || location == null || location.isEmpty()) {
            return;
        }

        String content = JsonUtils.toJson(entities);

        File destFile = FilesUtils.getDataSource(location, valueClazz);
        Files.writeString(destFile.toPath(), content);
    }
}
