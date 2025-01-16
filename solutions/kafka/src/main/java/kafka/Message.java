package kafka;

public class Message {
    private final String id;
    private final byte[] value;
    private final long timestamp;

    public Message(String id, byte[] value) {
        this.id = id;
        this.value = value;
        this.timestamp = System.nanoTime();
    }

    public String getId() {
        return id;
    }

    public byte[] getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
