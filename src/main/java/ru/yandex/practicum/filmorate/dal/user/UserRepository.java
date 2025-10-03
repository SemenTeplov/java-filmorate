package ru.yandex.practicum.filmorate.dal.user;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbc;
    private final UserRowMapper mapper;

    private static final String ADD_QUERY =
            "INSERT INTO users (name, login, email, birthday) VALUES ('%s', '%s', '%s', '%s')";
    private static final String ADD_FRIENDS_QUERY =
            "INSERT INTO friends (user_id, friend_id) VALUES ('%d', '%d')";
    private static final String REMOVE_QUERY =
            "DELETE FROM users WHERE id = '%d'";
    private static final String REMOVE_FRIEND_QUERY =
            "DELETE FROM friends WHERE user_id = %d";
    private static final String UPDATE_QUERY =
            "UPDATE users SET name = '%s', login = '%s', email = '%s', birthday = '%s' WHERE id = '%d'";
    private static final String GET_QUERY =
            "SELECT * FROM users WHERE id = '%d'";
    private static final String GET_FRIENDS_QUERY =
            "SELECT friend_id FROM friends WHERE user_id = '%d'";
    private static final String GET_ALL_QUERY =
            "SELECT * FROM users";

    public User add(User user) {
        String query = String.format(ADD_QUERY,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday());

        jdbc.execute(query);

        user.getFriends().forEach(f -> {
            jdbc.execute(String.format(ADD_FRIENDS_QUERY, user.getId(), f));
        });

        return user;
    }

    public User remove(User user) {
        jdbc.execute(String.format(REMOVE_QUERY, user.getId()));
        jdbc.execute(String.format(REMOVE_FRIEND_QUERY, user.getId()));

        return user;
    }

    public User update(User user) {
        if (user.getId() == null) {
            return add(user);
        }

        String query = String.format(UPDATE_QUERY,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        jdbc.execute(query);
        jdbc.execute(String.format(REMOVE_FRIEND_QUERY, user.getId()));

        user.getFriends().forEach(f -> {
            jdbc.execute(String.format(ADD_FRIENDS_QUERY, user.getId(), f));
        });

        return user;
    }

    public User get(Integer id) {
        try {
            User user = jdbc.query(String.format(GET_QUERY, id), mapper).getFirst();

            jdbc.queryForList(String.format(GET_FRIENDS_QUERY, id), Integer.class).forEach(user::addFriend);

            return user;
        } catch (Exception e) {
            throw new NotFoundException("Not found");
        }
    }

    public Collection<User> getAll() {
        return jdbc.query(GET_ALL_QUERY, mapper).stream().peek(u -> jdbc
                .queryForList(String.format(GET_FRIENDS_QUERY, u.getId()), Integer.class)
                .forEach(u::addFriend))
                .collect(Collectors.toSet());
    }
}
