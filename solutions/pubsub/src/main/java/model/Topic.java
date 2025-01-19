package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Topic {
    private final String id;
    private final String name;
    private final List<Message> messages;

    public Topic(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.messages = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized long addMessage(Message msg) {
        int size = messages.size();
        messages.add(msg);
        return size;
    }

    public Message getMessage(long offset) {
        if (offset >= messages.size()) {
            throw new IllegalArgumentException("Message doesn't exist at requested offset");
        }
        return messages.get((int) offset);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCurrentOffset() {
        return messages.size();
    }
}
