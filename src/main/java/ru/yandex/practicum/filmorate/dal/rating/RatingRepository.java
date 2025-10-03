package ru.yandex.practicum.filmorate.dal.rating;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.RatingFilm;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class RatingRepository {
    private final JdbcTemplate jdbc;
    private final RatingRowMapper mapper;

    private static final String ADD_QUERY =
            "INSERT INTO ratings (id, name) VALUES (%d, '%s')";
    private static final String REMOVE_QUERY =
            "DELETE FROM ratings WHERE id = '%d'";
    private static final String GET_QUERY =
            "SELECT * FROM ratings WHERE id = '%d'";
    private static final String GET_ALL_QUERY =
            "SELECT * FROM ratings";

    public RatingFilm add(RatingFilm rating) {
        String query = String.format(ADD_QUERY,
                rating.getId(),
                rating.getName());

        jdbc.execute(query);

        return rating;
    }

    public RatingFilm remove(RatingFilm rating) {
        jdbc.execute(String.format(REMOVE_QUERY, rating.getId()));

        return rating;
    }

    public RatingFilm get(int id) {
        return jdbc.query(String.format(GET_QUERY, id), mapper).getFirst();
    }

    public Collection<RatingFilm> getAll() {
        return jdbc.query(GET_ALL_QUERY, mapper);
    }
}
