package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.util.Rating;

import java.util.Collection;

public interface FilmStorage {
    Film add(Film film);

    Film remove(Film film);

    Film update(Film film);

    Film get(Integer id);

    Collection<Film> getAll();
}
