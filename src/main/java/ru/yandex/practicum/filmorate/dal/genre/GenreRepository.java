package ru.yandex.practicum.filmorate.dal.genre;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class GenreRepository {
    private final JdbcTemplate jdbc;
    private final GenreRowMapper mapper;

    public Genre add(Genre genre) {
        String query = String.format(Queries.ADD_QUERY,
                genre.getId(),
                genre.getName());

        jdbc.execute(query);

        return genre;
    }

    public Genre remove(Genre genre) {
        jdbc.execute(String.format(Queries.REMOVE_QUERY, genre.getId()));

        return genre;
    }

    public Genre get(Integer id) {
        try {
            return jdbc.query(String.format(Queries.GET_QUERY, id), mapper).getFirst();
        } catch (Exception e) {
            throw new NotFoundException("Not found");
        }
    }

    public Collection<Genre> getAll() {
        return jdbc.query(Queries.GET_ALL_QUERY, mapper);
    }
}
