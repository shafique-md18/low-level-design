package model;

import service.PubSubService;

import java.util.UUID;

public class SimplePublisher implements Publisher {
    private final String id;
    private final PubSubService pubSubService;

    public SimplePublisher(PubSubService pubSubService) {
        this.id = UUID.randomUUID().toString();
        this.pubSubService = pubSubService;
    }

    @Override
    public void publish(String topicId, Message message) {
        pubSubService.publish(topicId, message, this);
    }

    @Override
    public String getId() {
        return id;
    }
}
