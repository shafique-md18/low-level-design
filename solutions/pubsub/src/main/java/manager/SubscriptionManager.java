package manager;

import model.Subscriber;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SubscriptionManager {
    private final Map<String, List<Subscriber>> topicSubscribers;
    private final Map<String, Map<String, AtomicLong>> subscriberTopicOffsets;

    public SubscriptionManager() {
        topicSubscribers = new ConcurrentHashMap<>();
        subscriberTopicOffsets = new ConcurrentHashMap<>();
    }

    public void addSubscriber(String topicId, Subscriber subscriber) {
        topicSubscribers.computeIfAbsent(topicId, _ -> Collections.synchronizedList(new ArrayList<>())).add(subscriber);
        subscriberTopicOffsets.computeIfAbsent(subscriber.getId(), _ -> new ConcurrentHashMap<>())
                .computeIfAbsent(topicId, _ -> new AtomicLong(0L));
    }

    public void removeSubscriber(String topicId, Subscriber subscriber) {
        topicSubscribers.computeIfPresent(topicId, (_, subscribers) -> {
            subscribers.remove(subscriber);
            return subscribers;
        });

        subscriberTopicOffsets.computeIfPresent(subscriber.getId(), (_, topicOffsets) -> {
            topicOffsets.remove(topicId);
            return topicOffsets;
        });
    }

    public List<Subscriber> getSubscribers(String topicId) {
        return topicSubscribers.getOrDefault(topicId, new ArrayList<>());
    }

    public long getOffset(String topicId, String subscriberId) {
        AtomicLong offset = subscriberTopicOffsets.getOrDefault(topicId, new ConcurrentHashMap<>()).get(subscriberId);
        return offset != null ? offset.get() : 0;
    }

    public boolean compareAndSetOffset(String topicId, String subscriberId, long expected, long newValue) {
        AtomicLong offset = subscriberTopicOffsets.getOrDefault(topicId, new HashMap<>()).get(subscriberId);
        return offset != null && offset.compareAndSet(expected, newValue);
    }
}
