package redis;

import redis.eviction.LRUCacheEvictionPolicy;
import redis.storage.InMemoryMapCacheStorage;

public class CacheFactory<K, V> {
    public Cache<K, V> defaultCache(final int capacity) {
        return new Cache<K, V>(new LRUCacheEvictionPolicy<>(), new InMemoryMapCacheStorage<>(capacity));
    }
}
