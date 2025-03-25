package org.example.ngevaticketmanagerspring.user;

import java.util.List;

import org.example.ngevaticketmanagerspring.logging.EventLogger;
import org.example.ngevaticketmanagerspring.utils.exceptions.ExceptionMessageShortener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final EventLogger eventLogger;

    private final UserService service;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            User createdUser = service.createUser(user);
            eventLogger.logEvent("New user with ID: " + createdUser.getId() + " was created.", true);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Unique index or primary key violation") && e.getMessage().contains("EMAIL")) {
                eventLogger.logEvent("Creation of new user failed. Reason: Email " + user.getEmail() + " already exists.", false);
            } else {
                eventLogger.logEvent(
                    "Creation of new user failed. Reason: " + ExceptionMessageShortener.extractBetweenQuotes(e.getMessage()), false);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = service.updateUser(id, user);
            eventLogger.logEvent("User with ID: " + id + " was updated.", true);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Unique index or primary key violation") && e.getMessage().contains("EMAIL")) {
                eventLogger.logEvent("Update of user with ID: " + id + " failed. Reason: Email " + user.getEmail() + " already exists.",
                    false);
            } else {
                eventLogger.logEvent("Update of user with ID: " + id + " failed. Reason: " + e.getMessage(), false);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            service.deleteUser(id);
            eventLogger.logEvent("Successfully deleted user with ID : " + id + ".", true);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted user with ID: " + id + ".");
        } catch (RuntimeException e) {
            eventLogger.logEvent("Deletion of user with ID: " + id + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable Long id) {
        try {
            final User user = service.findUserById(id);
            eventLogger.logEvent("Retrieved user with ID : " + id + ".", true);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            eventLogger.logEvent("Retrieval of user with ID: " + id + " failed. Reason: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> allUsers = service.findAllUsers();
        eventLogger.logEvent("Retrieved all users.", true);
        return ResponseEntity.ok(allUsers);
    }
}



