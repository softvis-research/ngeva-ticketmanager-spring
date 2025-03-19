package org.example.ngevaticketmanagerspring.ticket;

import org.example.ngevaticketmanagerspring.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {

    private final TicketService service;

    @PostMapping("/{eventId}/{userId}")
    public ResponseEntity<Object> purchaseTicket(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            Ticket createdTicket = service.purchaseTicket(eventId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long ticketId) {
        try {
            service.cancelTicket(ticketId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted ticket with ID: " + ticketId + ".");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> findTicketsByUserId(@PathVariable Long userId) {
        try {
            List<TicketsByUserDto> ticketList = service.findTicketsByUserId(userId);
            return ResponseEntity.ok(Map.of(
                "total", ticketList.size(),
                "tickets", ticketList
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Object> findTicketsByEventId(@PathVariable Long eventId) {
        try {
            return ResponseEntity.ok(service.findTicketsByEventId(eventId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}
