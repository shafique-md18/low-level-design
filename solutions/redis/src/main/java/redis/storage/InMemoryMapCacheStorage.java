package redis.storage;

import redis.exception.StorageFullException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryMapCacheStorage<K, V> implements CacheStorage<K, V> {
    private final Map<K, V> cache;
    private final int capacity;
    private final ReentrantLock lock;

    public InMemoryMapCacheStorage(int capacity) {
        this.capacity = capacity;
        this.cache = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public void put(K key, V value) {
        lock.lock(); // For concurrent storage checks and cache addition
        try {
            if (isStorageFull()) {
                throw new StorageFullException("In-memory cache storage is full");
            }
            cache.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V get(K key) {
        return cache.get(key); // ConcurrentHashMap handles this safely
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    private boolean isStorageFull() {
        return cache.size() >= capacity;
    }
}
