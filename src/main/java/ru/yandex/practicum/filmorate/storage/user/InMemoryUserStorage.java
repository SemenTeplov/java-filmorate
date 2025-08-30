package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User add(User user) {
        log.info("User created");

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(users.size() + 1);
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User remove(User user) {
        if (users.containsKey(user.getId())) {
            log.info("Film deleted");

            return users.remove(user.getId());
        }
        log.info("Film not found");

        return null;
    }

    @Override
    public User update(User user) {
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

    @Override
    public User get(Integer id) {
        if (users.containsKey(id)) {
            log.info("Got user");

            return users.get(id);
        }

        throw new NotFoundException("User not found");
    }

    @Override
    public Collection<User> getAll() {
        log.info("Got users");

        return users.values();
    }
}
