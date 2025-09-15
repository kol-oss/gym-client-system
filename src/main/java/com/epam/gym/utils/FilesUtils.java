package com.epam.gym.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FilesUtils {
    private static final String DATA_SOURCE_EXTENSION = ".json";

    public static File getDataSource(String location, String name) {
        Path path = Paths.get(location, name.toLowerCase() + DATA_SOURCE_EXTENSION);
        return path.toFile();
    }

    public static File getDataSource(String location, Class<?> clazz) {
        return getDataSource(location, clazz.getSimpleName().toLowerCase());
    }
}
