package web.pulso.utils;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

    private static ApplicationEventPublisher publisher;

    public EventDispatcher(ApplicationEventPublisher publisher) {
        EventDispatcher.publisher = publisher;
    }

    @Async
    public static <T> void publish(T event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }
}