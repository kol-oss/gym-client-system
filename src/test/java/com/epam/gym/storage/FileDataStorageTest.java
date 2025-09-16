package com.epam.gym.storage;

import com.epam.gym.exception.DataReadException;
import com.epam.gym.exception.DataWriteException;
import com.epam.gym.platform.FilePlatform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileDataStorageTest {
    @Mock
    private FilePlatform filePlatform;

    @Test
    public void givenValidJsonContent_whenInit_thenMapFilled() throws IOException {
        // Arrange
        final String location = "mock";
        final boolean saveUpdates = false;

        final String key = "Key";
        final String value = "Value";
        final String content = "{\"" + key + "\":\"" + value + "\"}";

        FileDataStorage<String, String> storage = new FileDataStorage<>(String.class, String.class, location, saveUpdates);
        storage.setFilePlatform(filePlatform);

        when(filePlatform.readDataFile(any())).thenReturn(content);

        // Act
        storage.init();

        // Assert
        List<String> values = storage.selectAll();

        assertEquals(1, values.size());
        assertEquals(value, values.getFirst());
        verify(filePlatform, times(1)).readDataFile(any());
    }

    @Test
    public void givenInvalidJsonContent_whenInit_thenThrowDataRead() throws IOException {
        // Arrange
        final String location = "mock";
        final boolean saveUpdates = false;

        final String content = "invalid_json";

        FileDataStorage<String, String> storage = new FileDataStorage<>(String.class, String.class, location, saveUpdates);
        storage.setFilePlatform(filePlatform);

        when(filePlatform.readDataFile(any())).thenReturn(content);

        // Act & Assert
        assertThrows(DataReadException.class, storage::init);
        List<String> values = storage.selectAll();

        assertEquals(0, values.size());
        verify(filePlatform, times(1)).readDataFile(any());
    }

    @Test
    public void givenValidJsonContent_whenDestroy_thenWriteToFileCalled() throws IOException {
        // Arrange
        final String location = "mock";
        final boolean saveUpdates = true;

        final String key = "Key";
        final String value = "Value";
        final String content = "{\"" + key + "\":\"" + value + "\"}";

        FileDataStorage<String, String> storage = new FileDataStorage<>(String.class, String.class, location, saveUpdates);
        storage.setFilePlatform(filePlatform);

        storage.insert(key, value);

        // Act
        storage.destroy();

        // Assert
        verify(filePlatform, times(1)).writeDataFile(any(), eq(content));
    }

    @Test
    public void givenNotSaveUpdates_whenDestroy_thenDoNotWrite() throws IOException {
        // Arrange
        final String location = "mock";
        final boolean saveUpdates = false; // save updates is disabled

        final String key = "Key";
        final String value = "Value";

        FileDataStorage<String, String> storage = new FileDataStorage<>(String.class, String.class, location, saveUpdates);
        storage.setFilePlatform(filePlatform);

        storage.insert(key, value);

        // Act
        storage.destroy();

        // Assert
        verify(filePlatform, times(0)).writeDataFile(any(), any());
    }

    @Test
    public void givenProblemWithFileWriting_whenDestroy_thenThrowDataWrite() throws IOException {
        // Arrange
        final String location = "mock";
        final boolean saveUpdates = true;

        final String key = "Key";
        final String value = "Value";

        FileDataStorage<String, String> storage = new FileDataStorage<>(String.class, String.class, location, saveUpdates);
        storage.setFilePlatform(filePlatform);

        doThrow(IOException.class).when(filePlatform).writeDataFile(any(), any());

        storage.insert(key, value);

        // Act & Assert
        assertThrows(DataWriteException.class, storage::destroy);
        verify(filePlatform, times(1)).writeDataFile(any(), any());
    }
}
