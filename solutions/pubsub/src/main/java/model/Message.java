package model;

import java.util.UUID;

public class Message {
    private final String id;
    private final byte[] content;
    private final long timestamp;

    public Message(byte[] content) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", content=" + new String(content) +
                ", timestamp=" + timestamp +
                '}';
    }
}
