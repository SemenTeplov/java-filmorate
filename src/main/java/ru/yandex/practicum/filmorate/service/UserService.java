package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserStorage users;

    @Autowired
    public UserService(UserStorage users) {
        this.users = users;
    }

    public User create(User user) {
        log.info("User added");
        return users.add(user);
    }

    public User update(User user) {
        log.info("User updated");
        return users.update(user);
    }

    public void addFriend(Integer userId, Integer friendId) {
        log.info("Now they are friends");
        users.get(userId).addFriend(friendId);
        users.get(friendId).addFriend(userId);
    }

    public User remove(User user) {
        log.info("Film deleted");
        return users.remove(user);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        log.info("They quarreled");
        users.get(userId).removeFriend(friendId);
        users.get(friendId).removeFriend(userId);
    }

    public Collection<User> getAll() {
        log.info("Got users");
        return users.getAll();
    }

    public Set<User> getFriends(Integer userId) {
        log.info("Got friends for user: {}", userId);

        return users
                .getAll()
                .stream()
                .filter(user -> users.get(userId).getFriends().contains(user.getId()))
                .collect(Collectors.toSet());
    }

    public Set<User> getAllFriends(Integer userId, Integer friendId) {
        log.info("Got friends for users: {} and {}", userId, friendId);

        return users
                .get(userId)
                .getFriends()
                .stream()
                .filter(f -> users.get(friendId).getFriends().contains(f))
                .map(users::get)
                .collect(Collectors.toSet());
    }
}
