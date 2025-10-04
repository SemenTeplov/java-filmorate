package ru.yandex.practicum.filmorate.dal.user;

public class Queries {
    private Queries() {

    }

    public static final String ADD_QUERY =
            "INSERT INTO users (id, name, login, email, birthday) VALUES (%d, '%s', '%s', '%s', '%s')";
    public static final String ADD_FRIENDS_QUERY =
            "INSERT INTO friends (user_id, friend_id) VALUES ('%d', '%d')";

    public static final String REMOVE_QUERY =
            "DELETE FROM users WHERE id = '%d'";
    public static final String REMOVE_FRIEND_QUERY =
            "DELETE FROM friends WHERE user_id = %d";

    public static final String UPDATE_QUERY =
            "UPDATE users SET name = '%s', login = '%s', email = '%s', birthday = '%s' WHERE id = '%d'";

    public static final String GET_QUERY =
            "SELECT * FROM users WHERE id = '%d'";
    public static final String GET_FRIENDS_QUERY =
            "SELECT friend_id FROM friends WHERE user_id = '%d'";
    public static final String GET_ALL_QUERY =
            "SELECT * FROM users";
}
