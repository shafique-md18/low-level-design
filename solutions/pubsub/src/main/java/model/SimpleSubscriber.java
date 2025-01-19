package model;

import service.PubSubService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SimpleSubscriber implements Subscriber {
    private final String id;
    private final PubSubService pubSubService;

    public SimpleSubscriber(PubSubService pubSubService) {
        this.id = UUID.randomUUID().toString();
        this.pubSubService = pubSubService;
    }

    @Override
    public void subscribe(String topicId) {
        pubSubService.subscribe(topicId, this);
    }

    @Override
    public void unsubscribe(String topicId) {
        pubSubService.unsubscribe(topicId, this);
    }

    @Override
    public CompletableFuture<Boolean> onMessage(Message message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.printf("Subscriber <%s> received message - %s\n", id, message);
                // some processing
                return true;
            } catch (Exception e) {
                System.err.printf("Error processing message for subscriber <%s>: %s\n",
                        id, e.getMessage());
                return false;
            }
        });
    }

    @Override
    public String getId() {
        return id;
    }
}
