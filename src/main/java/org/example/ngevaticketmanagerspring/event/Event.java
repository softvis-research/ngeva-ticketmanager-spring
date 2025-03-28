package org.example.ngevaticketmanagerspring.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.example.ngevaticketmanagerspring.idgenerator.annotation.CustomIdGeneratorAnnotation;
import org.example.ngevaticketmanagerspring.ticket.Ticket;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "event")
public class Event {

    public Event() {}

    public Event(final String title, final String place, final LocalDate date, int quota) {
        this.title = title;
        this.place = place;
        this.date = date;
        this.quota = quota;
        this.tickets = new ArrayList<>();
    }

    @Setter(AccessLevel.NONE)
    @Id
    @CustomIdGeneratorAnnotation
    @Column(nullable = false, unique = true)
    private Long id;

    @NotBlank(message = "Title must be set.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Place must be set.")
    @Column(nullable = false)
    private String place;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @FutureOrPresent(message = "Date must not be in the past.")
    @NotNull(message = "Date must be set.")
    @Column(nullable = false)
    private LocalDate date;

    @PositiveOrZero(message = "The quota must not be negative.")
    @NotNull(message = "Quota >= 0 must be set.")
    @Column(nullable = false)
    private Integer quota;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
