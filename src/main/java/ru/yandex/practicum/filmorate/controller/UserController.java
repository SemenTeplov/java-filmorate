package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

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
        return checkName(user);
    }

    @PutMapping("/{id}")
    public User update(@Valid @RequestBody User user) {
        log.info("User updated");
        return checkName(user);
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
