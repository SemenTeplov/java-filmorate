package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film add(Film film) {
        log.info("Film added");
        film.setId(films.size() + 1);
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film remove(Film film) {
        if (films.containsKey(film.getId())) {
            log.info("Film deleted");

            return films.remove(film.getId());
        }
        log.info("Film not found");

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

            log.info("Film updated");
            return value;
        }

        throw new NotFoundException("Film not found");
    }

    @Override
    public Film get(Integer id) {
        if (films.containsKey(id)) {
            log.info("Got film");

            return films.get(id);
        }

        throw new NotFoundException("Film not found");
    }

    @Override
    public Collection<Film> getAll() {
        log.info("Got films");

        return films.values();
    }
}
