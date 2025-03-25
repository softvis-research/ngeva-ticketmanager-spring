package org.example.ngevaticketmanagerspring.event;

import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

import org.example.ngevaticketmanagerspring.ticket.Ticket;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    private final String exceptionText = "Could not find event with ID: ";

    public Event createEvent(Event event) {
        return repository.save(event);
    }

    public Event updateEvent(long eventId, Event newEvent) {
        Optional<Event> opt = repository.findById(eventId);
        if (opt.isEmpty()) {
            throw new RuntimeException(exceptionText + eventId + ".");
        }
        Event event = opt.get();
        event.setTitle(newEvent.getTitle() != null ? newEvent.getTitle() : event.getTitle());
        event.setPlace(newEvent.getPlace() != null ? newEvent.getPlace() : event.getPlace());
        event.setDate(newEvent.getDate() != null ? newEvent.getDate() : event.getDate());
        if (newEvent.getQuota() != null) {
            int newQuota = newEvent.getQuota();
            if (newQuota < 0) {
                throw new RuntimeException("New quota cannot be negative.");
            }
            event.setQuota(newQuota);
        }

        return repository.save(event);
    }

    public void deleteEvent(Long id) {
        Optional<Event> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException(exceptionText + id + ".");
        }
        repository.delete(opt.get());
    }

    public void deleteAllEvents() {
        repository.deleteAll();
    }

    public Event findEventById(Long id) {
        Optional<Event> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException(exceptionText + id + ".");
        }
        return opt.get();
    }

    public Boolean hasTicketsLeft(Long eventId) {
        int quota = repository.getQuotaByEventId(eventId).orElseThrow(() -> new RuntimeException(exceptionText + eventId + "."));
        return (quota > 0);
    }

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    @Transactional
    public void reserveTicket(Event event) {
        int quota = event.getQuota();
        if (quota <= 0) {
            throw new RuntimeException("There is no quota left for this event.");
        }
        event.setQuota(event.getQuota() - 1);
        repository.save(event);
    }

    public void releaseTicket(Long eventId, Ticket ticket) {
        Event event = findEventById(eventId);
        List<Ticket> tickets = event.getTickets();
        tickets.remove(ticket);
        event.setTickets(tickets);
        event.setQuota(event.getQuota() + 1);
        repository.save(event);
    }
}
