package kafka;

import kafka.exception.TopicNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO: To support different types of consumers, can create Consumer interface
public class Consumer {
    private final MessageBroker broker;
    private final Map<String, Integer> topicOffsets;

    public Consumer(MessageBroker broker) {
        this.broker = broker;
        this.topicOffsets = new ConcurrentHashMap<>();
    }

    public synchronized List<Message> poll(String topicId, int maxMessages) {
        Topic t = broker.getTopic(topicId);
        if (t == null) {
            throw new TopicNotFoundException("Topic not found while polling for messages");
        }

        int currentOffset = topicOffsets.getOrDefault(topicId, 0);
        List<Message> messages = t.read(currentOffset, maxMessages);

        topicOffsets.put(topicId, currentOffset + messages.size());

        return messages;
    }

    public void seek(String topicId, int newOffset) {
        topicOffsets.put(topicId, newOffset);
    }
}
