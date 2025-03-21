package org.example.ngevaticketmanagerspring.ticket;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TicketsByUserDto {

    @NotBlank
    private final Long id;

    @NotBlank
    private final String eventTitle;

    @NotBlank
    private final String eventPlace;

    @NotBlank
    private final LocalDate eventDate;

    public TicketsByUserDto(final Ticket ticket) {
        this.id = ticket.getId();
        this.eventTitle = ticket.getEvent().getTitle();
        this.eventPlace = ticket.getEvent().getPlace();
        this.eventDate = ticket.getEvent().getDate();
    }

    public String toString() {
        return "Ticket{" + "id=" + id + ", eventTitle=" + eventTitle + ", eventPlace=" + eventPlace + ", eventDate=" + eventDate + '}';
    }
}
