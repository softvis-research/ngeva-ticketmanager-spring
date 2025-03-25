package org.example.ngevaticketmanagerspring.ticket;

import java.util.List;
import java.util.Map;

import org.example.ngevaticketmanagerspring.logging.EventLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {

    private final EventLogger eventLogger;

    private final TicketService service;

    @PostMapping("/{eventId}/{userId}")
    public ResponseEntity<Object> purchaseTicket(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            Ticket createdTicket = service.purchaseTicket(eventId, userId);
            eventLogger.logEvent(
                "User with ID: " + userId + " has purchased ticket with ID " + createdTicket.getId() + " from event with" + " ID " + eventId
                    + ".", true);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
        } catch (RuntimeException e) {
            eventLogger.logEvent("Ticket purchase failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long ticketId) {
        try {
            service.cancelTicket(ticketId);
            eventLogger.logEvent("Ticket with ID " + ticketId + " has been cancelled.", true);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted ticket with ID: " + ticketId + ".");
        } catch (RuntimeException e) {
            eventLogger.logEvent("Cancellation of ticket with ID " + ticketId + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> findTicketsByUserId(@PathVariable Long userId) {
        try {
            List<TicketsByUserDto> ticketList = service.findTicketsByUserId(userId);
            eventLogger.logEvent("Retrieved all tickets for user with ID " + userId + ".", true);
            return ResponseEntity.ok(Map.of("total", ticketList.size(), "tickets", ticketList));
        } catch (RuntimeException e) {
            eventLogger.logEvent("Retrieval of tickets for user with ID " + userId + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Object> findTicketsByEventId(@PathVariable Long eventId) {
        try {
            final List<Ticket> tickets = service.findTicketsByEventId(eventId);
            eventLogger.logEvent("Retrieved all tickets for event with ID " + eventId + ".", true);
            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            eventLogger.logEvent("Retrieval of tickets for event with ID " + eventId + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}
