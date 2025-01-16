package kafka;

import kafka.exception.TopicNotFoundException;

// TODO: To support different types of producers, can create Producer interface
public class Producer {
    private final MessageBroker broker;

    public Producer(MessageBroker broker) {
        this.broker = broker;
    }

    public int send(String topicId, String messageId, byte[] messageValue) {
        Topic t = broker.getTopic(topicId);
        if (t == null) {
            throw new TopicNotFoundException("Topic not found while sending message");
        }
        return t.append(messageId, messageValue);
    }
}
