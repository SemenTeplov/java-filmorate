package ru.yandex.practicum.filmorate.dal.genre;

public class Queries {
    private Queries() {

    }

    public static final String ADD_QUERY =
            "INSERT INTO genres (id, name) VALUES ('%d', '%s')";
    public static final String REMOVE_QUERY =
            "DELETE FROM genres WHERE id = '%d'";
    public static final String GET_QUERY =
            "SELECT * FROM genres WHERE id = '%d'";
    public static final String GET_ALL_QUERY =
            "SELECT * FROM genres";
}
