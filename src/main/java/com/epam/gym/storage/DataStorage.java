package com.epam.gym.storage;

public interface DataStorage<K, T> {
    T select(K key);

    void insert(K key, T value);

    void update(K key, T value);

    T delete(K key);
}
