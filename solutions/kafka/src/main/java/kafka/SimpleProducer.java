package kafka;

import kafka.exception.TopicNotFoundException;

public class SimpleProducer implements Producer {
    private final MessageBroker broker;

    public SimpleProducer(MessageBroker broker) {
        this.broker = broker;
    }

    public void offer(String topicId, byte[] message) {
        Topic t = broker.getTopic(topicId);
        if (t == null) {
            throw new TopicNotFoundException("Topic not found while sending message");
        }
        t.append(message);
    }
}
