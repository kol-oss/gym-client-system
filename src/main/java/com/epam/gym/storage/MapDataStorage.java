package com.epam.gym.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapDataStorage<K, T> implements DataStorage<K, T> {
    protected final Map<K, T> entities;

    protected final Class<K> keyClazz;
    protected final Class<T> valueClazz;

    public MapDataStorage(Class<K> keyClazz, Class<T> valueClazz) {
        this.entities = new ConcurrentHashMap<>();

        this.keyClazz = keyClazz;
        this.valueClazz = valueClazz;
    }

    public T select(K key) {
        return entities.get(key);
    }

    public List<T> selectAll() {
        return new ArrayList<>(entities.values());
    }

    public void insert(K key, T value) {
        entities.put(key, value);
    }

    public void update(K key, T value) {
        entities.put(key, value);
    }

    public T delete(K key) {
        T deleted = entities.get(key);
        entities.remove(key);

        return deleted;
    }
}
