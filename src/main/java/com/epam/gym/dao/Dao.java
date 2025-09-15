package com.epam.gym.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    Optional<T> findById(K key);

    List<T> findAll();

    void insert(K key, T value);

    void update(K key, T value);

    Optional<T> delete(K key);
}
