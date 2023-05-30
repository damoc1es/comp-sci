package org.example;

import org.model.Event;

import java.util.UUID;

public class StartClientREST {
    private final static EventsClient eventsClient = new EventsClient();

    public static void main(String[] args) { // testing the client
        Event event = new Event(UUID.randomUUID(), 444);
        try {
            show(() -> eventsClient.create(event));

            show(() -> {
                Event[] events = eventsClient.getAll();
                for(Event e : events) {
                    System.out.println(e.getId() + ":" + e);
                }
            });
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        show(() -> eventsClient.getById("1d1a68da-4938-4216-9104-3dbdbf0463c8"));
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
