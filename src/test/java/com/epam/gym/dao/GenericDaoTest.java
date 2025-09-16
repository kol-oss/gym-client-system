package com.epam.gym.dao;

import com.epam.gym.dao.impl.GenericDao;
import com.epam.gym.storage.DataStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenericDaoTest {
    @Mock
    private DataStorage<String, String> storage;

    @InjectMocks
    private GenericDao<String, String> dao;

    @Test
    public void givenExistingRecord_whenFindById_thenReturnRecord() {
        // Arrange
        final String key = "Key";
        final String value = "Value";

        when(storage.select(key)).thenReturn(value);

        // Act
        Optional<String> result = dao.findById(key);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(value, result.get());
        verify(storage).select(key);
    }

    @Test
    public void givenNonExistingRecord_whenFindById_thenReturnEmptyOptional() {
        // Arrange
        final String key = "NonExistingKey";

        when(storage.select(key)).thenReturn(null);

        // Act
        Optional<String> result = dao.findById(key);

        // Assert
        assertTrue(result.isEmpty());
        verify(storage).select(key);
    }

    @Test
    public void givenRecordsExist_whenFindAll_thenReturnAllRecords() {
        // Arrange
        List<String> expectedValues = List.of("Value1", "Value2", "Value3");

        when(storage.selectAll()).thenReturn(expectedValues);

        // Act
        List<String> result = dao.findAll();

        // Assert
        assertEquals(expectedValues, result);
        assertEquals(expectedValues.size(), result.size());
        verify(storage).selectAll();
    }

    @Test
    public void givenEmptyStorage_whenFindAll_thenReturnEmptyList() {
        // Arrange
        List<String> emptyList = List.of();

        when(storage.selectAll()).thenReturn(emptyList);

        // Act
        List<String> result = dao.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(storage).selectAll();
    }

    @Test
    public void givenKeyValue_whenInsert_thenCallStorageInsert() {
        // Arrange
        final String key = "NewKey";
        final String value = "NewValue";

        // Act
        dao.insert(key, value);

        // Assert
        verify(storage).insert(key, value);
    }

    @Test
    public void givenExistingKeyAndValue_whenUpdate_thenCallStorageUpdate() {
        // Arrange
        final String key = "ExistingKey";
        final String updatedValue = "UpdatedValue";

        // Act
        dao.update(key, updatedValue);

        // Assert
        verify(storage).update(key, updatedValue);
    }

    @Test
    public void givenExistingRecord_whenDelete_thenReturnDeletedRecord() {
        // Arrange
        final String key = "KeyToDelete";
        final String value = "ValueToDelete";

        when(storage.delete(key)).thenReturn(value);

        // Act
        Optional<String> result = dao.delete(key);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(value, result.get());
        verify(storage).delete(key);
    }

    @Test
    public void givenNonExistingRecord_whenDelete_thenReturnEmptyOptional() {
        // Arrange
        final String key = "NonExistingKey";

        when(storage.delete(key)).thenReturn(null);

        // Act
        Optional<String> result = dao.delete(key);

        // Assert
        assertTrue(result.isEmpty());
        verify(storage).delete(key);
    }
}