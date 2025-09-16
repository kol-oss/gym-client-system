package com.epam.gym.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FilePlatformImpl implements FilePlatform {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String readDataFile(Path path) throws IOException {
        if (path == null) {
            logger.warn("File can not be read because provided path is null");
            throw new FileNotFoundException("File can not be read because provided path is null");
        }

        File sourceFile = path.toFile();
        if (!sourceFile.exists()) {
            logger.warn("File via path {} does not exist", sourceFile.getPath());
            throw new FileNotFoundException("File can not be read because provided path is null");
        }

        if (sourceFile.isDirectory()) {
            logger.error("File via path {} is a directory", sourceFile.getPath());
            throw new FileNotFoundException("File can not be read because provided path is a directory");
        }

        return Files.readString(sourceFile.toPath());
    }

    @Override
    public void writeDataFile(Path path, String data) throws IOException {
        if (path == null) {
            logger.warn("File can not be written because provided path is null");
            throw new FileNotFoundException("File can not be written because provided path is null");
        }

        Files.writeString(path, data);
    }
}
