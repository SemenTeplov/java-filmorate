package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.manager.StorageManager;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage films;
    private final UserStorage users;

    @Autowired
    public FilmService() {
        this.films = StorageManager.filmStorage;
        this.users = StorageManager.userStorage;
    }

    public Film add(Film film) {
        return films.add(film);
    }

    public void addLike(Integer filmId, Integer userId) {
        log.info("Like added");
        films.get(filmId).addLike(users.get(userId).getId());
    }

    public Film update(Film film) {
        return films.update(film);
    }

    public Film remove(Film film) {
        return films.remove(film);
    }

    public void removeLike(Integer filmId, Integer userId) {
        log.info("Like removed");
        films.get(filmId).removeLike(users.get(userId).getId());
    }

    public Collection<Film> getAll() {
        return films.getAll();
    }

    public List<Film> getFilms(Integer count) {
        log.info("Got {} popular films", count);

        return films
                .getAll()
                .stream()
                .filter(f -> !f.getLikes().isEmpty())
                .sorted(Comparator.comparingInt(film -> film.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList())
                .reversed();
    }
}
