package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Genre add(Genre genre);

    Genre remove(Genre genre);

    Genre get(Integer id);

    Collection<Genre> getAll();
}
