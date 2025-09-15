package com.epam.gym.storage;

import java.util.List;

public interface DataStorage<K, T> {
    T select(K key);

    List<T> selectAll();

    void insert(K key, T value);

    void update(K key, T value);

    T delete(K key);
}
