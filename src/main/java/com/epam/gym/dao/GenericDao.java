package com.epam.gym.dao;

import com.epam.gym.storage.DataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class GenericDao<K, T> implements Dao<K, T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected DataStorage<K, T> storage;

    protected abstract void setStorage(DataStorage<K, T> storage);

    @Override
    public Optional<T> findById(K key) {
        logger.debug("Selected key {} from storage", key);

        T result = storage.select(key);
        return Optional.ofNullable(result);
    }

    @Override
    public List<T> findAll() {
        List<T> result = storage.selectAll();
        logger.debug("Selected all ({}) values from storage", result.size());

        return result;
    }

    @Override
    public void insert(K key, T value) {
        logger.debug("Inserting value by key {} to storage", key);

        storage.insert(key, value);
    }

    @Override
    public void update(K key, T value) {
        logger.debug("Updating value by key {} in storage", key);

        storage.update(key, value);
    }

    @Override
    public Optional<T> delete(K key) {
        logger.debug("Deleting value by key {} in storage", key);

        T result = storage.delete(key);
        return Optional.ofNullable(result);
    }
}
