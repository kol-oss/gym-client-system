package com.epam.gym.platform;

import java.io.IOException;
import java.nio.file.Path;

public interface FilePlatform {
    String readDataFile(Path path) throws IOException;

    void writeDataFile(Path path, String data) throws IOException;
}
