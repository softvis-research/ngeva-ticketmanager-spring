package org.example.ngevaticketmanagerspring.ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

import org.example.ngevaticketmanagerspring.event.Event;
import org.example.ngevaticketmanagerspring.event.EventService;
import org.example.ngevaticketmanagerspring.user.User;
import org.example.ngevaticketmanagerspring.user.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final EventService eventService;

    private final UserService userService;

    private final TicketRepository ticketRepository;

    private final String exceptionText = "Could not find %s with ID: %d";

    @Transactional
    public Ticket purchaseTicket(Long eventId, Long userId) {

        Event event = eventService.findEventById(eventId);
        User user = userService.findUserById(userId);

        int MAX_TICKETS_PER_USER_AND_EVENT = 5;
        if (ticketRepository.numberOfTicketsByEventAndUser(eventId, userId) >= MAX_TICKETS_PER_USER_AND_EVENT) {
            throw new RuntimeException(
                String.format("User %d has already bought the maximum of %d tickets for event %d", userId, MAX_TICKETS_PER_USER_AND_EVENT,
                    eventId));
        }

        Ticket ticket = new Ticket(event, user, LocalDate.now());
        eventService.reserveTicket(event);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void cancelTicket(Long ticketId) {
        Ticket ticket = findTicketById(ticketId);
        Long eventId =
            Optional.ofNullable(ticket.getEvent()).map(Event::getId).orElseThrow(() -> new RuntimeException("Event or eventId is null"));
        eventService.releaseTicket(eventId, ticket);
        ticketRepository.delete(ticket);
    }

    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(exceptionText, "Ticket", id)));
    }

    public List<TicketsByUserDto> findTicketsByUserId(Long userId) {
        return userService.findUserById(userId).getTickets().stream().map(TicketsByUserDto::new).toList();
    }

    public List<Ticket> findTicketsByEventId(Long eventId) {
        return eventService.findEventById(eventId).getTickets();
    }
}
