package org.example.ngevaticketmanagerspring.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final String exceptionText = "Could not find user with ID: ";

    public User createUser(User user) {
        return repository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            User updatedUser = opt.get();
            if (user.getName() != null) {
                updatedUser.setName(user.getName());
            }
            if (user.getBirthday() != null) {
                updatedUser.setBirthday(user.getBirthday());
            }
            if (user.getEmail() != null) {
                updatedUser.setEmail(user.getEmail());
            }
            return repository.save(updatedUser);
        } else {
            throw new RuntimeException(exceptionText + id + ".");
        }
    }

    public void deleteUser(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException(exceptionText + id + ".");
        }
    }

    public User findUserById(Long id) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new RuntimeException(exceptionText + id + ".");
        }
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }
}

