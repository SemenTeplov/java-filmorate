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

    private Integer id = 1;

    public User add(User user) {
        user.setId(id++);

        String query = String.format(Queries.ADD_QUERY,
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday());

        jdbc.execute(query);

        setFriends(user);

        return user;
    }

    public User remove(User user) {
        jdbc.execute(String.format(Queries.REMOVE_QUERY, user.getId()));
        jdbc.execute(String.format(Queries.REMOVE_FRIEND_QUERY, user.getId()));

        return user;
    }

    public User update(User user) {
        if (user.getId() == null) {
            return add(user);
        }

        String query = String.format(Queries.UPDATE_QUERY,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        jdbc.execute(query);
        jdbc.execute(String.format(Queries.REMOVE_FRIEND_QUERY, user.getId()));

        setFriends(user);

        return user;
    }

    public User get(Integer id) {
        try {
            User user = jdbc.query(String.format(Queries.GET_QUERY, id), mapper).getFirst();

            jdbc.queryForList(String.format(Queries.GET_FRIENDS_QUERY, user.getId()), Integer.class).forEach(user::addFriend);

            return user;
        } catch (Exception e) {
            throw new NotFoundException("Not found");
        }
    }

    public Collection<User> getAll() {
        return jdbc.query(Queries.GET_ALL_QUERY, mapper).stream().peek(u -> jdbc
                .queryForList(String.format(Queries.GET_FRIENDS_QUERY, u.getId()), Integer.class)
                .forEach(u::addFriend))
                .collect(Collectors.toList());
    }

    private void setFriends(User user) {
        user.getFriends().forEach(f -> {
            jdbc.execute(String.format(Queries.ADD_FRIENDS_QUERY, user.getId(), f));
        });
    }
}
