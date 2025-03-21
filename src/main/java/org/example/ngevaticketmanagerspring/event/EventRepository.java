package org.example.ngevaticketmanagerspring.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e.quota FROM Event e WHERE e.id = :eventId")
    Optional<Integer> getQuotaByEventId(@Param("eventId") Long eventId);
}
