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

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
//        for (User value : users) {
//            if (value.getId() == user.getId()) {
//                value.setName(user.getName());
//                value.setLogin(user.getLogin());
//                value.setEmail(user.getEmail());
//                value.setBirthday(user.getBirthday());
//
//                log.info("User updated");
//                return value;
//            }
//        }
//
//        throw new ValidationException("User not found");

        log.info("User updated");
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Got users");

        return users;
    }
}
