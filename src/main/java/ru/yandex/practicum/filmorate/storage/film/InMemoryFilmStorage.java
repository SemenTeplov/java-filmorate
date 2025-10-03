package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.util.Rating;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film add(Film film) {
        film.setId(films.size() + 1);
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film remove(Film film) {
        if (films.containsKey(film.getId())) {
            return films.remove(film.getId());
        }

        return null;
    }

    @Override
    public Film update(Film film) {

        if (films.containsKey(film.getId())) {
            Film value = films.get(film.getId());

            value.setName(film.getName());
            value.setDescription(film.getDescription());
            value.setReleaseDate(film.getReleaseDate());
            value.setDuration(film.getDuration());

            return value;
        }

        throw new NotFoundException("Film not found");
    }

    @Override
    public Film get(Integer id) {
        if (films.containsKey(id)) {

            return films.get(id);
        }

        throw new NotFoundException("Film not found");
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }
}
