package ru.yandex.practicum.filmorate.dal.film;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.dal.genre.GenreRowMapper;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

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

    public Film add(Film film) {
        if (film.getMpa() != null && film.getMpa().getId() >= jdbc.queryForList(ru.yandex.practicum.filmorate.dal.rating.Queries.GET_ALL_QUERY).size()) {
            throw new NotFoundException("Not found");
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

        film.getLikes().forEach(l -> {
            jdbc.execute(String.format(Queries.ADD_LIKES_QUERY, film.getId(), l));
        });

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

        film.getLikes().forEach(l -> {
            jdbc.execute(String.format(Queries.ADD_LIKES_QUERY, film.getId(), l));
        });

        return film;
    }

    public Film get(Integer id) {
        Film film = jdbc.query(String.format(Queries.GET_QUERY, id), mapper).getFirst();

        film.setGenres(getGenres(film.getId()));

        film.getMpa().setName(jdbc.queryForList(String.format(Queries.GET_MPA_QUERY, film.getMpa().getId()), String.class).getFirst());

        jdbc.queryForList(String.format(Queries.GET_LIKES_QUERY, id), Integer.class).forEach(film::addLike);

        return film;
    }

    public Collection<Film> getAll() {
        return jdbc.query(Queries.GET_ALL_QUERY, mapper).stream().peek(f -> {
            f.setGenres(getGenres(f.getId()));
            jdbc.queryForList(String.format(Queries.GET_LIKES_QUERY, f.getId()), Integer.class).forEach(f::addLike);

            if (f.getMpa() != null) {
                f.getMpa().setName(jdbc.queryForList(String.format(Queries.GET_MPA_QUERY, f.getMpa().getId()), String.class).getFirst());
            }
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
        film.getGenres().forEach(g -> {
            jdbc.execute(String.format(Queries.ADD_OR_UPDATE_FILM_AND_GENRE_QUERY, film.getId(), g.getId(), film.getId(), g.getId()));
        });
    }
}
