## Redis

### Requirements

1. The LRU cache should support the following operations:
2. put(key, value): Insert a key-value pair into the cache. If the cache is at capacity, remove the least recently used item before inserting the new item.
3. get(key): Get the value associated with the given key. If the key exists in the cache, move it to the front of the cache (most recently used) and return its value. If the key does not exist, return -1.
4. The cache should have a fixed capacity, specified during initialization.
5. The cache should be thread-safe, allowing concurrent access from multiple threads.
6. The cache should be efficient in terms of time complexity for both put and get operations, ideally O(1)

### Class diagram

### Other considerations
