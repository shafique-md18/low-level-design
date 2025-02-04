package kafka;

import java.util.List;

public interface Consumer {
    List<Message> poll(String topicId, int maxMessages);
}
