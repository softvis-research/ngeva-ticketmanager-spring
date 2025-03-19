package org.example.ngevaticketmanagerspring.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.example.ngevaticketmanagerspring.event.Event;
import org.example.ngevaticketmanagerspring.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "ticket")
public class Ticket {

    public Ticket() {}

    public Ticket(final Event event, final User user, LocalDate purchaseDate) {
        this.event = event;
        this.user = user;
        this.purchaseDate = purchaseDate;
    }

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @PastOrPresent(message = "PurchaseDate must not be in the future.")
    @NotNull(message = "PurchaseDate must be set.")
    @Column(nullable = false)
    private LocalDate purchaseDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
