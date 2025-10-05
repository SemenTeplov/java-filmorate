package ru.yandex.practicum.filmorate.dal.rating;

public class Queries {
    private Queries() {

    }

    public static final String ADD_QUERY =
            "INSERT INTO ratings (id, name) VALUES (%d, '%s')";
    public static final String REMOVE_QUERY =
            "DELETE FROM ratings WHERE id = '%d'";
    public static final String GET_QUERY =
            "SELECT * FROM ratings WHERE id = '%d'";
    public static final String GET_ALL_QUERY =
            "SELECT * FROM ratings";
}
