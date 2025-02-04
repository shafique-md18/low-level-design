package kafka;

import java.util.List;

public class KafkaDemo {
    public static void main(String[] args) throws InterruptedException {
        concurrencyTest();
    }

    private static void simpleTest() {
        Topic topic1 = new Topic("topic1");
        Topic topic2 = new Topic("topic2");
        MessageBroker broker = new MessageBroker("broker1");
        broker.addTopic(topic1);
        broker.addTopic(topic2);

        // Create producers
        SimpleProducer producer1 = new SimpleProducer(broker);
        SimpleProducer producer2 = new SimpleProducer(broker);

        // Create consumers
        SimpleConsumer consumer1 = new SimpleConsumer(broker);
        SimpleConsumer consumer2 = new SimpleConsumer(broker);
        SimpleConsumer consumer3 = new SimpleConsumer(broker);
        SimpleConsumer consumer4 = new SimpleConsumer(broker);
        SimpleConsumer consumer5 = new SimpleConsumer(broker);

        // Publishing messages
        System.out.println("Publishing messages...");
        producer1.offer("topic1", "Message 1".getBytes());
        producer1.offer("topic1", "Message 2".getBytes());
        producer2.offer("topic1", "Message 3".getBytes());
        producer1.offer("topic2", "Message 4".getBytes());
        producer2.offer("topic2", "Message 5".getBytes());

        // Reading from topic1 (all consumers)
        System.out.println("\nReading from topic1 for two messages:");
        printMessages("Consumer 1", consumer1.poll("topic1", 10));
        printMessages("Consumer 2", consumer2.poll("topic1", 10));
        printMessages("Consumer 3", consumer3.poll("topic1", 10));
        printMessages("Consumer 4", consumer4.poll("topic1", 10));
        printMessages("Consumer 5", consumer5.poll("topic1", 2));
        printMessages("Consumer 5", consumer5.poll("topic1", 4));


        // Reading from topic2 (consumers 1, 3, and 4)
        System.out.println("\nReading from topic2:");
        printMessages("Consumer 1", consumer1.poll("topic2", 10));
        printMessages("Consumer 3", consumer3.poll("topic2", 10));
        printMessages("Consumer 4", consumer4.poll("topic2", 10));
    }

    private static void printMessages(String consumerName, List<Message> messages) {
        System.out.println(consumerName + " received:");
        for (Message msg : messages) {
            System.out.println("  - " + new String(msg.getValue()));
        }
    }

    private static void concurrencyTest() throws InterruptedException {
        // Create topic
        Topic topic1 = new Topic("test-topic");
        MessageBroker broker = new MessageBroker("broker1");
        broker.addTopic(topic1);

        // Create 2 producers and 2 consumers
        SimpleProducer producer1 = new SimpleProducer(broker);
        SimpleProducer producer2 = new SimpleProducer(broker);
        SimpleConsumer consumer1 = new SimpleConsumer(broker);
        SimpleConsumer consumer2 = new SimpleConsumer(broker);

        // Producer 1: sends messages 1-5
        Thread p1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                String msg = "P1-Message" + i;
                producer1.offer("test-topic", msg.getBytes());
                System.out.println("Producer 1 sent: " + msg);
            }
        });

        // Producer 2: sends messages 6-10
        Thread p2 = new Thread(() -> {
            for (int i = 6; i <= 10; i++) {
                String msg = "P2-Message" + i;
                producer2.offer("test-topic", msg.getBytes());
                System.out.println("Producer 2 sent: " + msg);
            }
        });

        // Consumer 1: continuously polls for messages
        Thread c1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {  // Poll 3 times
                List<Message> messages = consumer1.poll("test-topic", 5);
                for (Message msg : messages) {
                    System.out.println("Consumer 1 received: " + new String(msg.getValue()));
                }
                try {
                    Thread.sleep(100);  // Small delay between polls
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Consumer 2: continuously polls for messages
        Thread c2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {  // Poll 3 times
                List<Message> messages = consumer2.poll("test-topic", 5);
                for (Message msg : messages) {
                    System.out.println("Consumer 2 received: " + new String(msg.getValue()));
                }
                try {
                    Thread.sleep(100);  // Small delay between polls
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start all threads
        p1.start();
        p2.start();
        c1.start();
        c2.start();

        // Wait for threads to finish
        p1.join();
        p2.join();
        c1.join();
        c2.join();
    }
}
