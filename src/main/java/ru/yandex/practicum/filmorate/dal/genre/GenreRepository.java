package ru.yandex.practicum.filmorate.dal.genre;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class GenreRepository {
    private final JdbcTemplate jdbc;
    private final GenreRowMapper mapper;

    private static final String ADD_QUERY =
            "INSERT INTO genres (id, genre) VALUES ('%d', '%s')";
    private static final String REMOVE_QUERY =
            "DELETE FROM genres WHERE id = '%d'";
    private static final String GET_QUERY =
            "SELECT * FROM genres WHERE id = '%d'";
    private static final String GET_ALL_QUERY =
            "SELECT * FROM genres";

    public Genre add(Genre genre) {
        String query = String.format(ADD_QUERY,
                genre.getId(),
                genre.getGenre());

        jdbc.execute(query);

        return genre;
    }

    public Genre remove(Genre genre) {
        jdbc.execute(String.format(REMOVE_QUERY, genre.getId()));

        return genre;
    }

    public Genre get(int id) {
        return jdbc.query(String.format(GET_QUERY, id), mapper).getFirst();
    }

    public Collection<Genre> getAll() {
        return jdbc.query(GET_ALL_QUERY, mapper);
    }
}
