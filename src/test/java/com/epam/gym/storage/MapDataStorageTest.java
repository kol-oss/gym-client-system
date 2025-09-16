package com.epam.gym.storage;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDataStorageTest {
    @Test
    public void givenEmptyStorage_whenSelectAll_thenReturnEmptyList() {
        // Arrange
        DataStorage<String, String> storage = new MapDataStorage<>(String.class, String.class);

        // Act
        List<String> result = storage.selectAll();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void givenExistingKey_whenSelect_thenReturnElement() {
        // Arrange
        final String key = "Key";
        final String value = "Value";

        DataStorage<String, String> storage = new MapDataStorage<>(String.class, String.class);
        storage.insert(key, value);

        // Act
        String result = storage.select(key);

        // Assert
        assertEquals(value, result);
    }

    @Test
    public void givenUniqueKey_whenInsert_thenNewElementInserted() {
        // Arrange
        final String key = "Key";
        final String value = "Value";

        DataStorage<String, String> storage = new MapDataStorage<>(String.class, String.class);

        // Act
        storage.insert(key, value);

        // Assert
        List<String> values = storage.selectAll();

        assertEquals(1, values.size());
        assertEquals(value, values.getFirst());
    }

    @Test
    public void givenExistingKey_whenUpdate_thenElementUpdated() {
        // Arrange
        final String key = "Key";
        final String value = "Value";
        final String newValue = "NewValue";

        DataStorage<String, String> storage = new MapDataStorage<>(String.class, String.class);
        storage.insert(key, value);

        // Act
        storage.update(key, newValue);

        // Assert
        List<String> values = storage.selectAll();

        assertEquals(1, values.size());
        assertEquals(newValue, values.getFirst());
    }

    @Test
    public void givenNotExistingKey_whenUpdate_thenNothingUpdated() {
        // Arrange
        final String key = "Key";
        final String newValue = "NewValue";

        DataStorage<String, String> storage = new MapDataStorage<>(String.class, String.class);
        // value was not inserted

        // Act
        storage.update(key, newValue);

        // Assert
        List<String> values = storage.selectAll();

        assertEquals(0, values.size());
    }

    @Test
    public void givenExistingKey_whenDelete_thenElementDeleted() {
        // Arrange
        final String key = "Key";
        final String value = "Value";

        DataStorage<String, String> storage = new MapDataStorage<>(String.class, String.class);
        storage.insert(key, value);

        // Act
        storage.delete(key);

        // Assert
        List<String> values = storage.selectAll();

        assertEquals(0, values.size());
    }
}
