package com.epam.gym.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FilesUtils {
    private static final String DATA_SOURCE_EXTENSION = ".json";

    public static Path getDataSource(String location, String name) {
        return Paths.get(location, name.toLowerCase() + DATA_SOURCE_EXTENSION);
    }

    public static Path getDataSource(String location, Class<?> clazz) {
        return getDataSource(location, clazz.getSimpleName().toLowerCase());
    }
}
