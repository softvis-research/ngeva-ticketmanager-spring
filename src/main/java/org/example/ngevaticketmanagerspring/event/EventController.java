package org.example.ngevaticketmanagerspring.event;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/event", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final EventService service;

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event createdEvent = service.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("{eventId}/{newQuota}")
    public ResponseEntity<Object> updateQuota(@PathVariable Long eventId, @PathVariable int newQuota) {
        try {
            Event updatedEvent = service.updateQuota(eventId, newQuota);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateEvent(@Valid @RequestBody Event event) {
        try {
            Event updatedEvent = service.updateEvent(event);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            service.deleteEvent(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted event with ID: " + id + ".");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllEvents() {
        service.deleteAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body("Deleted all events.");
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findEventById(@PathVariable Long id) {
        try {
          return ResponseEntity.ok(service.findEventById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> AllEvents = service.getAllEvents();
        return ResponseEntity.ok(AllEvents);
    }
}
