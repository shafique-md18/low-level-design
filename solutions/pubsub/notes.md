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
