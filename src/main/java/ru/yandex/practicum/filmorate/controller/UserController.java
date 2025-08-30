package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@Valid @RequestBody User user) {
        return service.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        service.addFriend(id, friendId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public User remove(@Valid @RequestBody User user) {
        return service.remove(user);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        service.removeFriend(id, friendId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getFriends(@PathVariable int id) {
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getFriends(@PathVariable int id, @PathVariable int friendId) {
        return service.getAllFriends(id, friendId);
    }
}
