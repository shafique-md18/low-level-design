package kafka;

public interface Producer {
    void offer(String topicId, byte[] message);
}
