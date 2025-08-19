package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("User created");

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(users.size() + 1);
        users.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            User value = users.get(user.getId());

            if (value.getId() == user.getId()) {
                value.setName(user.getName());
                value.setLogin(user.getLogin());
                value.setEmail(user.getEmail());
                value.setBirthday(user.getBirthday());

                log.info("User updated");
                return value;
            }
        }

        throw new NotFoundException("User not found");
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("Got users");

        return users.values();
    }
}
