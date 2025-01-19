package model;

import java.util.concurrent.CompletableFuture;

public interface Subscriber {
    void subscribe(String topicId);
    void unsubscribe(String topicId);
    CompletableFuture<Boolean> onMessage(Message message);
    String getId();
}
