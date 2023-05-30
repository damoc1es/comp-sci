package org.example;

import org.model.Event;
import org.server.repository.db.EventsDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/competition/events")
public class EventController {

    @Autowired
    private EventsDbRepo eventsRepo;

    @GetMapping
    public Collection<Event> getAll() {
        return (Collection<Event>) eventsRepo.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);
        Event event = eventsRepo.findById(UUID.fromString(id));
        if (event ==null)
            return new ResponseEntity<String>("Event not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    @PostMapping
    public Event create(@RequestBody Event event) {
        event.setId(UUID.randomUUID());
        eventsRepo.store(event);
        return event;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Event update(@RequestBody Event event, @PathVariable String id) {
        System.out.println("Updating event ...");
        eventsRepo.update(UUID.fromString(id), event);
        return event;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        System.out.println("Deleting event with id "+id);

        try {
            eventsRepo.remove(eventsRepo.findById(UUID.fromString(id)));
            return new ResponseEntity<Event>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Ctrl delete exception " + e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}