package ru.yandex.practicum.filmorate.dal.rating;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.RatingFilm;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class RatingRepository {
    private final JdbcTemplate jdbc;
    private final RatingRowMapper mapper;

    public RatingFilm add(RatingFilm rating) {
        String query = String.format(Queries.ADD_QUERY,
                rating.getId(),
                rating.getName());

        jdbc.execute(query);

        return rating;
    }

    public RatingFilm remove(RatingFilm rating) {
        jdbc.execute(String.format(Queries.REMOVE_QUERY, rating.getId()));

        return rating;
    }

    public RatingFilm get(int id) {
        try {
            return jdbc.query(String.format(Queries.GET_QUERY, id), mapper).getFirst();
        } catch (Exception e) {
            throw new NotFoundException("Not found");
        }
    }

    public Collection<RatingFilm> getAll() {
        return jdbc.query(Queries.GET_ALL_QUERY, mapper);
    }
}
