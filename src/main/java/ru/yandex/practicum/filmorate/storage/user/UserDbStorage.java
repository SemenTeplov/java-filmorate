package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.dal.user.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final UserRepository repository;

    public UserDbStorage(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User add(User user) {
        return repository.add(user);
    }

    @Override
    public User remove(User user) {
        return repository.remove(user);
    }

    @Override
    public User update(User user) {
        return repository.update(user);
    }

    @Override
    public User get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return repository.getAll();
    }
}
