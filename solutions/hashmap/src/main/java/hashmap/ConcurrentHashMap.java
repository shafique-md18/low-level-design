package hashmap;

import hashmap.exceptions.NullKeyException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentHashMap<K, V> {
    // We always support hashmap capacity in powers of 2
    private final int INITIAL_CAPACITY = 1 << 4;
    // This is maximum power of two in integer which is position, 1 << 31 is negative
    private final int MAX_CAPACITY = 1 << 30;
    // Resize the map if load goes beyond this
    private final double LOAD_FACTOR = 0.75;
    private int size = 0;
    private Entry<K, V>[] buckets;

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public ConcurrentHashMap() {
        buckets = newBuckets(INITIAL_CAPACITY);
    }

    public ConcurrentHashMap(int capacity) {
        capacity = convertToValidCapacity(capacity);
        buckets = newBuckets(capacity);
    }

    public V get(K key) {
        readLock.lock();
        try {
            if (key == null) {
                throw new NullKeyException("Key cannot be null in hashmap");
            }
            int idx = getBucketIndex(key);
            Entry<K, V> entry = buckets[idx];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
                entry = entry.next;
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public void put(K key, V value) {
        writeLock.lock();
        try {
            if (key == null) {
                throw new NullKeyException("Key cannot be null in hashmap");
            }
            if (hasExceededLoadFactor()) {
                resize();
            }
            int idx = getBucketIndex(key);
            Entry<K, V> newEntry = new Entry<>(key, value);

            // Check if Key already exists
            Entry<K, V> entry = buckets[idx];
            while (entry != null) {
                if (entry.key == key) {
                    entry.value = value;
                    return;
                }
                entry = entry.next;
            }

            // Add the new entry at the start of bucket
            if (buckets[idx] != null) {
                newEntry.next = buckets[idx];
            }

            buckets[idx] = newEntry;
            size++;
        } finally {
            writeLock.unlock();
        }
    }

    public V remove(K key) {
        writeLock.lock();
        try {
            if (key == null) {
                throw new NullKeyException("Key cannot be null in hashmap");
            }
            int idx = getBucketIndex(key);
            Entry<K, V> entry = buckets[idx];
            Entry<K, V> prev = null;
            while (entry != null) {
                if (entry.key.equals(key)) {
                    if (prev == null) {
                        buckets[idx] = entry.next;
                    } else {
                        prev.next = entry.next;
                    }
                    size--;
                    return entry.value;
                }
                prev = entry;
                entry = entry.next;
            }

            return null;
        } finally {
            writeLock.unlock();
        }
    }

    private void resize() {
        int capacity = buckets.length * 2;
        Entry<K, V>[] newBuckets = newBuckets(capacity);
        for (int i = 0; i < buckets.length; i++) {
            Entry<K, V> entry = buckets[i];
            while (entry != null) {
                // Remove entry from old buckets
                Entry<K, V> next = entry.next;
                buckets[i] = entry.next;

                // Add entry to the front of new buckets
                int idx = getBucketIndex(entry.key);
                entry.next = newBuckets[idx];
                newBuckets[i] = entry;

                entry = next;
            }
        }
        buckets = newBuckets;
    }

    public int getSize() {
        readLock.lock();
        try {
            return size;
        } finally {
            readLock.unlock();
        }
    }

    // To fetch the next (including current) integer which is power of 2
    private int convertToValidCapacity(int capacity) {
        // Subtracting 1 to handle capacity which is already power of 2
        int n = capacity - 1;
        // We're trying to fill all bits to the right of the leftmost 1 with 1s
        n |= (n >> 1);
        n |= (n >> 2);
        n |= (n >> 4);
        n |= (n >> 8);
        n |= (n >> 16);

        if (n <= 0) {
            return 1;
        } else if (n >= MAX_CAPACITY) {
            return MAX_CAPACITY;
        }
        return n + 1;
    }

    // For evenly spread hash of the key
    private int hash(K key) {
        int h;
        // We already have hashCode for finding the key, but we are doing h >>> 16
        // for evenly spread keys
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // For getting the bucket index of the key
    private int getBucketIndex(K key) {
        // Use AND operation instead of expensive MOD operation
        return hash(key) & (buckets.length - 1);
    }

    private boolean hasExceededLoadFactor() {
        return ((double) size / buckets.length) > LOAD_FACTOR;
    }

    public static class Entry<K, V> {
        Entry<K, V> next;
        K key;
        V value;

        Entry(K _key, V _value) {
            key = _key;
            value = _value;
        }
    }

    /*
         We get warning because java has limitations on creating an array of a generic type (Entry<K, V>[]) due to type safety
         Instead to get around this we can also create an ArrayList of buckets, but we are trading off type safety for performance
         This is because, generics are replaced during compile time -> This is called type erasure.
     */
    @SuppressWarnings("unchecked")
    private Entry<K, V>[] newBuckets(int capacity) {
        return new Entry[capacity];
    }
}
