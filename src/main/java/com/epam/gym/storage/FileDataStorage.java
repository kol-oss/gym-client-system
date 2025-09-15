package com.epam.gym.storage;

import com.epam.gym.utils.FilesUtils;
import com.epam.gym.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class FileDataStorage<K, T> extends MapDataStorage<K, T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String location;
    private final boolean saveUpdates;

    public FileDataStorage(Class<K> keyClazz, Class<T> valueClazz, String location, boolean saveUpdates) {
        super(keyClazz, valueClazz);

        this.location = location;
        this.saveUpdates = saveUpdates;
    }

    @PostConstruct
    public void init() {
        if (location == null || location.isEmpty()) {
            return;
        }

        File sourceFile = FilesUtils.getDataSource(location, valueClazz);
        if (!sourceFile.exists()) {
            logger.warn("Data source does via path {} does not exist", sourceFile.getPath());
            return;
        }

        if (sourceFile.isDirectory()) {
            logger.error("Data source does via path {} is a directory", sourceFile.getPath());
            return;
        }

        Map<K, T> parsed;
        try {
            String content = Files.readString(sourceFile.toPath());
            parsed = JsonUtils.fromJson(content, keyClazz, valueClazz);
        } catch (IOException ex) {
            throw new RuntimeException("Data source could not be parsed: " + ex.getMessage());
        }

        logger.debug("Loaded {} entries from file by path {}", parsed.size(), sourceFile.getPath());
        entities.putAll(parsed);
    }

    @PreDestroy
    public void destroy() {
        if (!saveUpdates || location == null || location.isEmpty()) {
            return;
        }

        File destFile;
        try {
            String content = JsonUtils.toJson(entities);

            destFile = FilesUtils.getDataSource(location, valueClazz);
            Files.writeString(destFile.toPath(), content);
        } catch (IOException ex) {
            logger.error("Data source could not be saved: {}", ex.getMessage());
            return;
        }

        logger.debug("Wrote {} entries to file by path {}", entities.size(), destFile.getPath());
    }
}
