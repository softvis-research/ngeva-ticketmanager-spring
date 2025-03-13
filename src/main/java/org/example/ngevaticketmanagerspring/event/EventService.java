package org.example.ngevaticketmanagerspring.event;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    private final String exceptionText = "Could not find event with ID: ";

    public Event createEvent(Event event) {
        return repository.save(event);
    }

    public Event updateQuota(Long id, int newQuota) {
        if (newQuota < 0) {
            throw new RuntimeException("New quota cannot be negative.");
        }

        Optional<Event> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException(exceptionText + id + ".");
        }

        Event event = opt.get();
        event.setQuota(newQuota);
        return repository.save(event);
    }

    public Event updateEvent(Event newEvent) {
        if (newEvent.getId() == null) {
            throw new RuntimeException("Event ID cannot be null.");
        }
        Optional<Event> opt = repository.findById(newEvent.getId());
        if (opt.isEmpty()) {
            throw new RuntimeException(exceptionText + newEvent.getId() + ".");
        }
        Event event = opt.get();

        event.setTitle(newEvent.getTitle() != null ? newEvent.getTitle() : event.getTitle());
        event.setPlace(newEvent.getPlace() != null ? newEvent.getPlace() : event.getPlace());
        event.setDate(newEvent.getDate() != null ? newEvent.getDate() : event.getDate());
        event.setQuota(newEvent.getQuota() != null ? newEvent.getQuota() : event.getQuota());

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

    public List<Event> getAllEvents() {
        return (List<Event>) repository.findAll();
    }


}
