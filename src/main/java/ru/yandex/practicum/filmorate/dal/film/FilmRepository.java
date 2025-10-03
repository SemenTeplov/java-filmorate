package ru.yandex.practicum.filmorate.dal.film;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FilmRepository {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper;

    private final static String ADD_QUERY =
            "INSERT INTO films (id, name, description, release, duration, rating) VALUES ('%d', '%s', '%s', '%s', '%d', '%s')";
    private final static String ADD_GENRE_QUERY =
            "INSERT INTO genres (genre) SELECT '%s' WHERE NOT EXISTS (SELECT 1 FROM genres WHERE genre = '%s')";
    private final static String ADD_OR_UPDATE_FILM_AND_GENRE =
            "INSERT INTO films_genres (film_id, genre_id) SELECT %d, SELECT id FROM genres WHERE genre = '%s' WHERE NOT EXISTS (SELECT * FROM films_genres WHERE film_id = '%d' AND genre_id = (SELECT id FROM genres WHERE genre = '%s'))";
    private final static String ADD_LIKES_QUERY =
            "INSERT INTO likes (film_id, user_id) VALUES ('%d', '%d')";
    private final static String REMOVE_QUERY =
            "DELETE FROM films WHERE id = '%d'";
    private final static String REMOVE_FILM_AND_GENRE =
            "DELETE FROM films_genres WHERE film_id = '%d'";
    private final static String REMOVE_LIKES_QUERY =
            "DELETE FROM likes WHERE film_id = '%d'";
    private final static String UPDATE_QUERY =
            "UPDATE films SET name = '%s', description = '%s', release = '%s', duration = '%d', rating = '%s' WHERE id = '%d'";
    private final static String GET_QUERY =
            "SELECT * FROM films WHERE id = '%d'";
    private final static String GET_GENRES =
            "SELECT genre FROM genres WHERE id = %d";
    private final static String GET_GENRES_FILM_AND_GENRE  =
            "SELECT genre_id FROM films_genres WHERE film_id = %d";
    private final static String GET_LIKES_QUERY =
            "SELECT user_id FROM likes WHERE film_id = '%d'";
    private final static String GET_ALL_QUERY =
            "SELECT * FROM films";

    public Film add(Film film) {
        String query = String.format(ADD_QUERY,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating());

        jdbc.execute(query);

        film.getGenre().forEach(g -> {
            jdbc.execute(String.format(ADD_GENRE_QUERY, g, g));
            jdbc.execute(String.format(ADD_OR_UPDATE_FILM_AND_GENRE, film.getId(), g, film.getId(), g));
        });

        film.getLikes().forEach(l -> {
            jdbc.execute(String.format(ADD_LIKES_QUERY, film.getId(), l));
        });

        return film;
    }

    public Film remove(Film film) {
        jdbc.execute(String.format(REMOVE_FILM_AND_GENRE, film.getId()));
        jdbc.execute(String.format(REMOVE_QUERY, film.getId()));
        jdbc.execute(String.format(REMOVE_LIKES_QUERY, film.getId()));

        return film;
    }

    public Film update(Film film) {
        String query = String.format(UPDATE_QUERY,
                        film.getName(),
                        film.getDescription(),
                        film.getReleaseDate(),
                        film.getDuration(),
                        film.getRating(),
                        film.getId());

        jdbc.execute(query);
        jdbc.execute(String.format(REMOVE_FILM_AND_GENRE, film.getId()));

        film.getGenre().forEach(g -> {
            jdbc.execute(String.format(ADD_GENRE_QUERY, g, g));
            jdbc.execute(String.format(ADD_OR_UPDATE_FILM_AND_GENRE, film.getId(), g, film.getId(), g));
        });

        jdbc.execute(String.format(REMOVE_LIKES_QUERY, film.getId()));

        film.getLikes().forEach(l -> {
            jdbc.execute(String.format(ADD_LIKES_QUERY, film.getId(), l));
        });

        return film;
    }

    public Film get(Integer id) {
        Film film = jdbc.query(String.format(GET_QUERY, id), mapper).getFirst();

        film.setGenre(jdbc.queryForList(String.format(GET_GENRES_FILM_AND_GENRE, id), Integer.class)
                .stream()
                .map(i -> jdbc.queryForObject(String.format(GET_GENRES, i), String.class))
                .toList());

        jdbc.queryForList(String.format(GET_LIKES_QUERY, id), Integer.class).forEach(film::addLike);

        return film;
    }

    public Collection<Film> getAll() {
        return jdbc.query(GET_ALL_QUERY, mapper).stream().peek(f -> {
            f.setGenre(jdbc.queryForList(String.format(GET_GENRES_FILM_AND_GENRE, f.getId()), Integer.class)
                    .stream()
                    .map(i -> jdbc.queryForObject(String.format(GET_GENRES, i), String.class))
                    .toList());
            jdbc.queryForList(String.format(GET_LIKES_QUERY, f.getId()), Integer.class).forEach(f::addLike);
        }).collect(Collectors.toSet());
    }
}
