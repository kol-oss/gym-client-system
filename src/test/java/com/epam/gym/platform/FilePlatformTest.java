package com.epam.gym.platform;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilePlatformTest {
    private FilePlatformImpl filePlatform;
    private Path tempFile;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        filePlatform = new FilePlatformImpl();

        tempFile = Files.createTempFile("test", ".txt");
        tempDir = Files.createTempDirectory("testDir");
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (Files.exists(tempFile)) {
            Files.delete(tempFile);
        }

        if (Files.exists(tempDir)) {
            Files.delete(tempDir);
        }
    }

    @Test
    public void givenNullPath_whenReadDataFile_thenThrowFileNotFoundException() {
        // Arrange
        final Path path = null;

        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> filePlatform.readDataFile(path));
    }

    @Test
    public void givenDirectory_whenReadDataFile_thenThrowFileNotFoundException() {
        // Arrange
        final Path path = tempDir;

        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> filePlatform.readDataFile(path));
    }

    @Test
    public void givenExistingFile_whenReadDataFile_thenReturnFileContent() throws IOException {
        // Arrange
        final String expectedContent = "Hello, world!";
        final Path path = tempFile;

        Files.writeString(tempFile, expectedContent);

        // Act
        String result = filePlatform.readDataFile(path);

        // Assert
        assertEquals(expectedContent, result);
    }

    @Test
    public void givenNullPath_whenWriteDataFile_thenThrowFileNotFoundException() {
        // Arrange
        final Path path = null;
        final String data = "data";

        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> filePlatform.writeDataFile(path, data));
    }

    @Test
    public void givenValidPath_whenWriteDataFile_thenFileContainsData() throws IOException {
        // Arrange
        final Path path = tempFile;
        final String data = "Test data";

        // Act
        filePlatform.writeDataFile(path, data);

        // Assert
        String fileContent = Files.readString(tempFile);
        assertEquals(data, fileContent);
    }

    @Test
    public void givenDirectory_whenWriteDataFile_thenThrowIOException() {
        // Arrange
        final Path path = tempDir;
        final String data = "Test data";

        // Act & Assert
        assertThrows(IOException.class, () -> filePlatform.writeDataFile(path, data));
    }
}
