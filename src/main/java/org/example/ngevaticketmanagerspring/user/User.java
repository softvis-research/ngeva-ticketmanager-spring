package org.example.ngevaticketmanagerspring.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.example.ngevaticketmanagerspring.idgenerator.annotation.CustomIdGeneratorAnnotation;
import org.example.ngevaticketmanagerspring.ticket.Ticket;
import org.example.ngevaticketmanagerspring.utils.validation.DateTimeframeConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    public User() {
    }

    public User(final String name, final LocalDate birthday, final String email) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.tickets = new ArrayList<>();
    }

    @Getter
    @Id
    @CustomIdGeneratorAnnotation
    @Column(nullable = false, unique = true)
    private Long id;

    @Getter
    @Setter
    @NotNull(message = "Name cannot be empty")
    @Column(nullable = false)
    @Pattern(regexp = "^((\\p{Lu}\\p{L}[-'\\p{L}]* \\p{Lu}\\p{L}[-'\\p{L}]*$)|(\\p{Lu}\\p{L}[-'\\p{L}]*, \\p{Lu}\\p{L}[-'\\p{L}]*$))", message = "Name must be in on of these formats: Firstname Lastname or Lastname, Firstname")
    private String name;

    @Getter
    @Setter
    @NotNull(message = "Birthday cannot be empty")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeframeConstraint()
    @Past(message = "Birthday has to be today or in the past")
    private LocalDate birthday;

    @Getter
    @Setter
    @Email(message = "Email must look like this: stuff@whatever.xyz")
    @NotNull(message = "Email cannot be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
