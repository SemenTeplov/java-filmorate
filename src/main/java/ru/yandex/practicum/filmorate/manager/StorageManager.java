package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

public class StorageManager {
    public static final FilmStorage filmStorage = new InMemoryFilmStorage();
    public static final UserStorage userStorage = new InMemoryUserStorage();
}
