package ru.yandex.practicum.filmorate.dal.film;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.dal.genre.GenreRowMapper;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.NotValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class FilmRepository {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper;
    private final GenreRowMapper genreMapper;

    private Integer id = 1;
    private Integer maxMpa = 5;
    private Integer maxGenre = 6;

    public Film add(Film film) {
        if (film.getMpa() != null && film.getMpa().getId() > maxMpa) {
            throw new NotValidationException("Rating don't validation");
        }

        film.setId(id++);

        String query = String.format(Queries.ADD_QUERY,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa() == null ? null : film.getMpa().getId());

        jdbc.execute(query);

        setGenres(film);
        setLikes(film);

        return film;
    }

    public Film remove(Film film) {
        jdbc.execute(String.format(Queries.REMOVE_FILM_AND_GENRE_QUERY, film.getId()));
        jdbc.execute(String.format(Queries.REMOVE_QUERY, film.getId()));
        jdbc.execute(String.format(Queries.REMOVE_LIKES_QUERY, film.getId()));

        return film;
    }

    public Film update(Film film) {
        if (jdbc.query(Queries.GET_ALL_QUERY, mapper).stream().noneMatch(f -> Objects.equals(f.getId(), film.getId()))) {
            throw new NotFoundException("Not found");
        }

        String query = String.format(Queries.UPDATE_QUERY,
                        film.getName(),
                        film.getDescription(),
                        film.getReleaseDate(),
                        film.getDuration(),
                        film.getMpa().getId(),
                        film.getId());

        jdbc.execute(query);
        jdbc.execute(String.format(Queries.REMOVE_FILM_AND_GENRE_QUERY, film.getId()));

        setGenres(film);

        jdbc.execute(String.format(Queries.REMOVE_LIKES_QUERY, film.getId()));

        setLikes(film);

        return film;
    }

    public Film get(Integer id) {
        Film film = jdbc.query(String.format(Queries.GET_QUERY, id), mapper).getFirst();

        film.setGenres(getGenres(film.getId()));

        jdbc.queryForList(String.format(Queries.GET_LIKES_QUERY, id), Integer.class).forEach(film::addLike);

        return film;
    }

    public Collection<Film> getAll() {
        return jdbc.query(Queries.GET_ALL_QUERY, mapper).stream().peek(f -> {
            f.setGenres(getGenres(f.getId()));
            jdbc.queryForList(String.format(Queries.GET_LIKES_QUERY, f.getId()), Integer.class).forEach(f::addLike);
        }).toList();
    }

    private List<Genre> getGenres(Integer id) {
        try {
            return jdbc.queryForList(String.format(Queries.GET_GENRES_FILM_AND_GENRE_QUERY, id), Integer.class)
                    .stream()
                    .map(genre_id -> jdbc.query(String.format(Queries.GET_GENRES_QUERY, genre_id), genreMapper).getFirst())
                    .toList();
        } catch (Exception e) {
            throw new NotFoundException("Not found");
        }
    }

    private void setGenres(Film film) {
        jdbc.batchUpdate(Queries.ADD_OR_UPDATE_FILM_AND_GENRE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                if (film.getGenres().get(i).getId() > maxGenre) {
                    throw new NotFoundException("Not found");
                }

                ps.setInt(1, film.getId());
                ps.setInt(2, film.getGenres().get(i).getId());
                ps.setInt(3, film.getId());
                ps.setInt(4, film.getGenres().get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
    }

    private void setLikes(Film film) {
        jdbc.batchUpdate(Queries.ADD_LIKES_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, film.getId());
                ps.setInt(2, film.getLikes().stream().toList().get(i));
            }

            @Override
            public int getBatchSize() {
                return film.getLikes().size();
            }
        });
    }
}
