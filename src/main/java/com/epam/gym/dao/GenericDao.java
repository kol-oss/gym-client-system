package com.epam.gym.dao;

import com.epam.gym.storage.DataStorage;

import java.util.List;
import java.util.Optional;

public abstract class GenericDao<K, T> implements Dao<K, T> {
    protected DataStorage<K, T> storage;

    protected abstract void setStorage(DataStorage<K, T> storage);

    @Override
    public Optional<T> findById(K key) {
        T result = storage.select(key);
        return Optional.ofNullable(result);
    }

    @Override
    public List<T> findAll() {
        return storage.selectAll();
    }

    @Override
    public void insert(K key, T value) {
        storage.insert(key, value);
    }

    @Override
    public void update(K key, T value) {
        storage.update(key, value);
    }

    @Override
    public Optional<T> delete(K key) {
        T result = storage.delete(key);
        return Optional.ofNullable(result);
    }
}
