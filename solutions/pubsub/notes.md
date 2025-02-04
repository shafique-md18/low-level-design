## Distributed Pub-Sub messaging queue

### Requirements

1. System must support multiple publishers that can publish messages to topics.
2. System must support multiple subscribers that can subscribe to topics.
3. A subscriber can subscribe to multiple topics.
4. Each message should be delivered to all subscribers of the topic in real-time.
5. Messages should maintain their order within a topic using offsets.
6. If a subscriber is offline, it should receive missed messages when it comes back online.
7. Messages should be delivered to subscribers in the same order they were published.
8. Each message should have a unique identifier, topic ID, content, and timestamp.
9. The system should support adding and removing subscribers from topics.
10. Message delivery should be asynchronous.
11. The system should be thread-safe and handle concurrent publishers and subscribers.
12. Failed message deliveries should be handled gracefully.
13. Topic and subscriber management operations should be thread-safe.
14. The system should maintain subscriber offset positions for each topic.
15. Each topic should have a unique identifier and name.

### Class diagram

### Other considerations

Some optimization on executor:
1. parallelStream() enables concurrent processing without waiting for each subscriber sequentially
2. Bounded queue (1000) vs unbounded LinkedBlockingQueue prevents potential OOM
3. CallerRunsPolicy handles backpressure vs silently queueing tasks

```java 
private final ExecutorService messageDeliveryExecutor = new ThreadPoolExecutor(
    5, 20, 60, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(1000),
    new ThreadPoolExecutor.CallerRunsPolicy()
);

private void notifySubscribers(String topicId, long messageOffset) {
    Topic topic = topicManager.getTopic(topicId);
    List<Subscriber> subscribers = subscriptionManager.getSubscribers(topic.getId());
    
    subscribers.parallelStream()
        .filter(subscriber -> subscriptionManager.getOffset(topic.getId(), subscriber.getId()) == messageOffset)
        .forEach(subscriber -> {
            messageDeliveryExecutor.submit(() -> {
                try {
                    Message msg = topic.getMessage(messageOffset);
                    subscriber.onMessage(msg)
                        .thenAccept(acknowledged -> {
                            if (acknowledged) {
                                subscriptionManager.compareAndSetOffset(topic.getId(), subscriber.getId(), messageOffset, messageOffset + 1);
                            }
                        });
                } catch (Exception e) {
                    log.error("Failed to process message for subscriber: {}", subscriber.getId(), e);
                }
            });
        });
}
```

Key optimization points to discuss:

Performance:
1. Batching notifications
2. Separate thread pools per topic
3. Async offset lookups
4. Configurable thread pool parameters

Reliability:
1. Retry mechanism with backoff
2. Dead letter queue
3. At-least-once delivery guarantees
4. Circuit breaker for failing subscribers

Monitoring:
1. Delivery success/failure metrics
2. Queue size monitoring
3. Latency tracking
4. Thread pool saturation alerts

Resource Management:
1. Bounded queues
2. Backpressure mechanisms
3. Topic-based partitioning
4. Memory consumption controls
