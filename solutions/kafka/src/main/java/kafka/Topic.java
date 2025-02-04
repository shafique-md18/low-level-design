package kafka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Topic {
    private final String id;
    private final List<Message> messages;

    public Topic(String id) {
        this.id = id;
        this.messages = Collections.synchronizedList(new ArrayList<>());
    }

    public void append(byte[] value) {
        // If the message should have publisher message creation timestamp or kafka message push timestamp
        // is a design decision
        Message message = new Message(UUID.randomUUID().toString(), value);
        messages.add(message);
    }

    public List<Message> read(int offset, int maxMessages) {
        if (offset >= messages.size()) {
            return new ArrayList<>();
        }
        int endIndex = Math.min(offset + maxMessages, messages.size());
        return new ArrayList<>(messages.subList(offset, endIndex));
    }

    public String getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
