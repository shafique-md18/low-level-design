package redis.eviction;

import redis.linkedlist.DoublyLinkedList;
import redis.linkedlist.DoublyLinkedListNode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCacheEvictionPolicy<K> implements EvictionPolicy<K> {
    private final DoublyLinkedList<K> dll;
    private final Map<K, DoublyLinkedListNode<K>> dllNodes;
    private final ReentrantLock lock;

    public LRUCacheEvictionPolicy() {
        dll = new DoublyLinkedList<>();
        dllNodes = new ConcurrentHashMap<>();
        lock = new ReentrantLock(true);
    }

    @Override
    public void keyAccessed(K key) {
        lock.lock();
        try {
            if (dllNodes.containsKey(key)) {
                dll.detach(dllNodes.get(key));
                dll.addLast(dllNodes.get(key));
            } else {
                DoublyLinkedListNode<K> node = dll.addLast(key);
                dllNodes.put(key, node);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public K evictKey() {
        lock.lock();
        try {
            DoublyLinkedListNode<K> first = dll.getFirst();
            if (first == null) {
                return null;
            }
            dll.detach(first);
            return dllNodes.remove(first.getElement()).getElement();
        } finally {
            lock.unlock();
        }
    }
}
