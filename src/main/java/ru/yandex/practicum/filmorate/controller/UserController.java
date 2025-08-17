package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("User created");
        users.add(user);

        return checkName(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        for (User value : users) {
            if (value.getId() == user.getId()) {
                value.setName(user.getName());
                value.setLogin(user.getLogin());
                value.setEmail(user.getEmail());
                value.setBirthday(user.getBirthday());

                log.info("User updated");
                return checkName(value);
            }
        }

        throw new ValidationException("User not found");
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Got users");
        return users;
    }

    private User checkName(User user) {
        if (user.getName().isBlank()) {
            log.debug("user's name changed user's login");
            user.setName(user.getLogin());
        }

        return user;
    }
}
