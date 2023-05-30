package org.example;

import org.model.Event;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class EventsClient {
    public static final String URL = "http://localhost:8080/competition/events";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Event[].class));
    }

    public Event getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Event.class));
    }

    public Event create(Event event) {
        return execute(() -> restTemplate.postForObject(URL, event, Event.class));
    }

    public void update(Event event) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, event.getId()), event);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
