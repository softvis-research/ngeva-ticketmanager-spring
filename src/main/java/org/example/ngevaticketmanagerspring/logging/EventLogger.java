package org.example.ngevaticketmanagerspring.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EventLogger {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final List<String> eventLogs = new ArrayList<>();

    public void logEvent(String event, boolean success) {
        String status = success ? "SUCCESS" : "FAILURE";
        eventLogs.add(
            LocalDateTime.now().format(dateFormatter) + " | " + Thread.currentThread().getStackTrace()[2].getMethodName() + " | " + status
                + " | " + event);
    }

    public List<String> getEventLogs() {
        return new ArrayList<>(eventLogs);
    }

    public void clearLogs() {
        eventLogs.clear();
    }
}

