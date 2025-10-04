package ru.yandex.practicum.filmorate.dal.film;

public class Queries {
    private Queries() {}

    public static final  String ADD_QUERY =
            "INSERT INTO films (id, name, description, release, duration, rating_id) VALUES ('%d', '%s', '%s', '%s', '%d', '%d')";
    public static final String ADD_OR_UPDATE_FILM_AND_GENRE_QUERY =
            "INSERT INTO films_genres (film_id, genre_id) SELECT '%d', '%d' WHERE NOT EXISTS (SELECT 1 FROM films_genres WHERE film_id = '%d' AND genre_id = '%d')";
    public static final String ADD_LIKES_QUERY =
            "INSERT INTO likes (film_id, user_id) VALUES ('%d', '%d')";

    public static final String REMOVE_QUERY =
            "DELETE FROM films WHERE id = '%d'";
    public static final String REMOVE_FILM_AND_GENRE_QUERY =
            "DELETE FROM films_genres WHERE film_id = '%d'";
    public static final String REMOVE_LIKES_QUERY =
            "DELETE FROM likes WHERE film_id = '%d'";

    public static final String UPDATE_QUERY =
            "UPDATE films SET name = '%s', description = '%s', release = '%s', duration = '%d', rating_id = '%d' WHERE id = '%d'";

    public static final String GET_QUERY =
            "SELECT * FROM films WHERE id = '%d'";
    public static final String GET_GENRES_QUERY =
            "SELECT * FROM genres WHERE id = %d";
    public static final String GET_GENRES_FILM_AND_GENRE_QUERY  =
            "SELECT genre_id FROM films_genres WHERE film_id = %d";
    public static final String GET_LIKES_QUERY =
            "SELECT user_id FROM likes WHERE film_id = '%d'";
    public static final String GET_MPA_QUERY =
            "SELECT name FROM ratings WHERE id = '%d'";
    public static final String GET_ALL_QUERY =
            "SELECT * FROM films";
}
