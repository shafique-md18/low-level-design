import model.*;
import service.PubSubService;

public class PubSubServiceDemo {
    public static void main(String[] args) {
        PubSubService service = new PubSubService();
        Topic sports = service.createTopic("Sports");
        Topic politics = service.createTopic("Politics");
        Topic hybridNews = service.createTopic("Hybrid News");

        Publisher sportsPublisher = new SimplePublisher(service);
        Publisher politicalPublisher = new SimplePublisher(service);
        Publisher hybridNewsPublisher = new SimplePublisher(service);
        Subscriber subscriber1 = new SimpleSubscriber(service);
        Subscriber subscriber2 = new SimpleSubscriber(service);
        Subscriber subscriber3 = new SimpleSubscriber(service);
        Subscriber subscriber4 = new SimpleSubscriber(service);

        subscriber1.subscribe(sports.getId());
        subscriber2.subscribe(politics.getId());
        subscriber3.subscribe(sports.getId());
        subscriber3.subscribe(politics.getId());
        subscriber4.subscribe(hybridNews.getId());

        for (int i = 0; i < 1; i++) {
            sportsPublisher.publish(sports.getId(), new Message("i".getBytes()));
            politicalPublisher.publish(politics.getId(), new Message("i".getBytes()));
            hybridNewsPublisher.publish(hybridNews.getId(), new Message("i".getBytes()));
        }
    }
}
