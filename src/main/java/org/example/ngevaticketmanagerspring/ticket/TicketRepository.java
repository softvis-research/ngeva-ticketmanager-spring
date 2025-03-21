package org.example.ngevaticketmanagerspring.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.event.id = :eventId AND t.user.id = :userId")
    long numberOfTicketsByEventAndUser(@Param("eventId") Long eventId, @Param("userId") Long userId);
}
