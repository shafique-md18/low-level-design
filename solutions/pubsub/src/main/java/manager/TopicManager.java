package manager;

import model.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TopicManager {
    private final Map<String, Topic> topics;

    public TopicManager() {
        this.topics = new ConcurrentHashMap<>();
    }

    public Topic createTopic(String name) {
        Topic topic = new Topic(name);
        this.topics.putIfAbsent(topic.getId(), topic);
        return topic;
    }

    public Topic getTopic(String topicId) {
        return this.topics.get(topicId);
    }

    public List<Topic> getAllTopics() {
        return new ArrayList<>(topics.values());
    }
}
