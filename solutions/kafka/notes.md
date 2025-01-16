## Kafka / Distributed Queue

Kafka is polling-based queue.
We have - 
1. Message - individual message in the queue.
2. Topic - a store which contains messages, can be thought of like a subject notebook which has pages (messages).
   1. Topic maintains an offset which indicates the page number of the last added message. As we are using List, we are using the size of list as offset.
3. MessageBroker - broker which manages a set of topics, each topic belongs to one broker this is so that the work is divided among brokers.
4. Producer - producer which publishes the message to the topic (topic is fetched via broker, message is pushed to topic directly)
5. Consumer - consumer which polls the messages from any topic (topic is fetched via broker)
    1. Consumer maintains a map of offsets for individual topics, this offset is like the page number till which notebook has been read.

### Requirements

1. The queue should be in-memory and should not require access to the file system.
2. There can be multiple topics in the queue.
3. A (string) message can be published on a topic by a producer/publisher and consumers/subscribers can subscribe to the topic to receive the messages.
4. There can be multiple producers and consumers.
5. A producer can publish to multiple topics.
6. A consumer can listen to multiple topics.
7. The consumer should print "<consumer_id> received <message>" on receiving the message.
8. The queue system should be multithreaded, i.e., messages can be produced or consumed in parallel by different producers/consumers.

### Other considerations

1. We can introduce interfaces for Producer and Consumer if we are expecting different implementations of it.
2. We can introduce partitions (partitioned-lists) for topics and add PartitionStrategy to get partition. -> Better performance as messages can be appended concurrently for separate partitions.
3. We can introduce ConsumerRegistry to introduce RBAC(Role Based Access Control) for individual topics based on topic type.
   1. We can move the logic to read messages to MessageBroker and the broker the validate access for the topic for the consumer.
