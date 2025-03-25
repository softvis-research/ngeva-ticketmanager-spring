package org.example.ngevaticketmanagerspring.event;

import java.util.List;

import org.example.ngevaticketmanagerspring.logging.EventLogger;
import org.example.ngevaticketmanagerspring.utils.exceptions.ExceptionMessageShortener;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/event", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final EventLogger eventLogger;

    private final EventService service;

    @PostMapping
    public ResponseEntity<Object> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = service.createEvent(event);
            eventLogger.logEvent("New event with ID: " + createdEvent.getId() + " was created.", true);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (RuntimeException e) {
            eventLogger.logEvent("Creation of new event failed. Reason: " + ExceptionMessageShortener.extractBetweenQuotes(e.getMessage()),
                false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        try {
            Event updatedEvent = service.updateEvent(id, event);
            eventLogger.logEvent("Event with ID: " + id + " was updated.", true);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            eventLogger.logEvent("Update of event with ID: " + id + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            service.deleteEvent(id);
            eventLogger.logEvent("Successfully deleted event with ID : " + id + ".", true);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted event with ID: " + id + ".");
        } catch (RuntimeException e) {
            eventLogger.logEvent("Deletion of event with ID: " + id + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllEvents() {
        service.deleteAllEvents();
        eventLogger.logEvent("Deleted all users.", true);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted all events.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findEventById(@PathVariable Long id) {
        try {
            final Event event = service.findEventById(id);
            eventLogger.logEvent("Retrieved event with ID " + id + ".", true);
            return ResponseEntity.ok(event);
        } catch (RuntimeException e) {
            eventLogger.logEvent("Retrieval of event with ID " + id + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{eventId}/tickets_left")
    public ResponseEntity<Object> hasTicketsLeft(@PathVariable Long eventId) {
        try {
            final Boolean hasLeftover = service.hasTicketsLeft(eventId);
            eventLogger.logEvent("Retrieved leftover tickets info of event with ID " + eventId + ".", true);
            return ResponseEntity.ok(hasLeftover);
        } catch (RuntimeException e) {
            eventLogger.logEvent("Retrieval of leftover tickets info of event with ID " + eventId + " failed. Reason: " + e.getMessage(),
                false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> AllEvents = service.getAllEvents();
        eventLogger.logEvent("Retrieved all events.", true);
        return ResponseEntity.ok(AllEvents);
    }
}
