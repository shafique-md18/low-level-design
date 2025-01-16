package redis.storage;

import java.util.Set;

public interface CacheStorage<K, V> {
    void put(K key, V value);
    V get(K key);
    V remove(K key);
    Set<K> keys();
}
