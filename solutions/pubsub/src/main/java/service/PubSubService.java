package service;

import manager.SubscriptionManager;
import manager.TopicManager;
import model.Message;
import model.Publisher;
import model.Subscriber;
import model.Topic;

import java.util.List;
import java.util.concurrent.*;

public class PubSubService {
    private final TopicManager topicManager;
    private final SubscriptionManager subscriptionManager;
    private final ExecutorService messageDeliveryExecutor;
    // TODO: We would need another ScheduledExecutorService for catching up subscribers with lost messages

    public PubSubService() {
        topicManager = new TopicManager();
        subscriptionManager = new SubscriptionManager();
        messageDeliveryExecutor = new ThreadPoolExecutor(1, 10, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    public Topic createTopic(String name) {
        System.out.printf("[PubSubService] Created topic: %s\n", name);
        return topicManager.createTopic(name);
    }

    public void subscribe(String topicId, Subscriber subscriber) {
        System.out.printf("[PubSubService] Subscriber <%s> subscribing to topic: <%s>\n", subscriber.getId(), topicId);
        subscriptionManager.addSubscriber(topicId, subscriber);
    }

    public void unsubscribe(String topicId, Subscriber subscriber) {
        subscriptionManager.removeSubscriber(topicId, subscriber);
    }

    public void publish(String topicId, Message message, Publisher publisher) {
        System.out.printf("[PubSubService] Publisher <%s> publishing message to topic <%s>:<%s>\n", publisher.getId(),
                topicId, message);
        Topic topic = topicManager.getTopic(topicId);
        if (topic != null) {
            long messageOffset = topic.addMessage(message);
            notifySubscribers(topicId, messageOffset);
        }
    }

    private void notifySubscribers(String topicId, long messageOffset) {
        Topic topic = topicManager.getTopic(topicId);
        List<Subscriber> topicSubscribers = subscriptionManager.getSubscribers(topic.getId());

        for (Subscriber subscriber : topicSubscribers) {
            // We only notify the consumer when he is on the latest message
            long currentOffset = subscriptionManager.getOffset(topic.getId(), subscriber.getId());

            if (currentOffset == messageOffset) {
                messageDeliveryExecutor.submit(() -> {
                   try {
                       System.out.printf("[PubSubService] Sending message to subscriber <%s>\n", subscriber.getId());
                       Message msg = topic.getMessage(currentOffset);
                       subscriber.onMessage(msg)
                                       .thenAccept(acknowledged -> {
                                          if (acknowledged) {
                                              subscriptionManager.compareAndSetOffset(topic.getId(), subscriber.getId(), currentOffset, currentOffset + 1);
                                          } else {
                                              // handle failed delivery
                                              System.out.println("Failed acknowledgement from subscriber");
                                          }
                                       });
                   } catch (Exception e) {
                       System.out.println("Failed to process message for subsriber - " + subscriber.getId());
                   }
                });
            }
        }
    }
}
