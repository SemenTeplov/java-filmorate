package ru.yandex.practicum.filmorate.storage.rating;

import ru.yandex.practicum.filmorate.model.RatingFilm;

import java.util.Collection;

public interface RatingStorage {
    RatingFilm add(RatingFilm rating);

    RatingFilm remove(RatingFilm rating);

    RatingFilm get(Integer id);

    Collection<RatingFilm> getAll();
}
