package kafka;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private final String id;
    private final List<Message> messages;

    public Topic(String id) {
        this.id = id;
        this.messages = new ArrayList<>();
    }

    public synchronized int append(String key, byte[] value) {
        Message message = new Message(key, value);
        messages.add(message);
        return messages.size() - 1;
    }

    public synchronized List<Message> read(int offset, int maxMessages) {
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
