package model;

public interface Publisher {
    void publish(String topicId, Message message);
    String getId();
}
