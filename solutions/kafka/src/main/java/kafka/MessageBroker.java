package kafka;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBroker {
    private final String id;
    private final Map<String, Topic> topics;

    public MessageBroker(String id) {
        this.id = id;
        this.topics = new ConcurrentHashMap<>();
    }

    public String getId() {
        return id;
    }

    public void addTopic(Topic topic) {
        topics.put(topic.getId(), topic);
    }

    public Topic removeTopic(String id) {
        return topics.remove(id);
    }

    public Topic getTopic(String id) {
        return topics.get(id);
    }
}
