package com.epam.gym.storage;

import com.epam.gym.exception.DataReadException;
import com.epam.gym.exception.DataWriteException;
import com.epam.gym.platform.FilePlatform;
import com.epam.gym.utils.FilesUtils;
import com.epam.gym.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class FileDataStorage<K, T> extends MapDataStorage<K, T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String location;
    private final boolean saveUpdates;

    @Setter
    private FilePlatform filePlatform;

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

        Path source = FilesUtils.getDataSource(location, valueClazz);
        Map<K, T> parsed;

        try {
            String content = filePlatform.readDataFile(source);
            if (content == null)
                return;
            
            parsed = JsonUtils.fromJson(content, keyClazz, valueClazz);
        } catch (IOException ex) {
            logger.error("Data source could not be parsed: {}", ex.getMessage());
            throw new DataReadException("Data source could not be parsed: " + ex.getMessage());
        }

        logger.debug("Loaded {} entries from file by path {}", parsed.size(), source);
        entities.putAll(parsed);
    }

    @PreDestroy
    public void destroy() {
        if (!saveUpdates) {
            return;
        }

        Path dest = FilesUtils.getDataSource(location, valueClazz);
        try {
            String content = JsonUtils.toJson(entities);
            filePlatform.writeDataFile(dest, content);
        } catch (IOException ex) {
            logger.error("Data source could not be saved: {}", ex.getMessage());
            throw new DataWriteException("Data source could not be saved: " + ex.getMessage());
        }

        logger.debug("Wrote {} entries to file by path {}", entities.size(), dest);
    }
}
