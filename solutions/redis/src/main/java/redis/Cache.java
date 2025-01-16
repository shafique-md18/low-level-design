package redis;

import redis.eviction.EvictionPolicy;
import redis.exception.StorageFullException;
import redis.storage.CacheStorage;

import java.util.concurrent.locks.ReentrantLock;

public class Cache<K, V> {
    private final EvictionPolicy<K> evictionPolicy;
    private final CacheStorage<K, V> cacheStorage;
    private final ReentrantLock lock;

    public Cache(EvictionPolicy<K> evictionPolicy, CacheStorage<K, V> cacheStorage) {
        this.evictionPolicy = evictionPolicy;
        this.cacheStorage = cacheStorage;
        this.lock = new ReentrantLock(true);
    }

    public void put(K key, V value) {
        lock.lock();
        try {
            putInternal(key, value, 1);
        } finally {
            lock.unlock();
        }
    }

    private void putInternal(K key, V value, int retryCount) {
        try {
            System.out.printf("Adding <%s, %s> to cache%n", key, value);
            cacheStorage.put(key, value);
            System.out.printf("Marking key accessed <%s>%n", key);
            evictionPolicy.keyAccessed(key);
        } catch (StorageFullException e) {
            if (retryCount <= 0) {
                throw new RuntimeException("Unable to put value in cache after retries");
            }
            System.out.printf("Cache storage full.%n");
            K evictedKey = evictionPolicy.evictKey();
            System.out.printf("Evicting and removing key <%s>%n", evictedKey);
            cacheStorage.remove(evictedKey);
            putInternal(key, value, retryCount - 1);
        }
    }

    public V get(K key) {
        lock.lock();
        try {
            System.out.printf("Fetching value for key <%s>%n", key);
            V val = cacheStorage.get(key);
            if (val == null) {
                return null;
            }
            System.out.printf("Marking key accessed <%s>%n", key);
            evictionPolicy.keyAccessed(key);
            return val;
        } finally {
            lock.unlock();
        }
    }
}
