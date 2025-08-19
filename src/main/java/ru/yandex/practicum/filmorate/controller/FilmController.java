package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        log.info("Film added");
        film.setId(films.size() + 1);
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {

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

    @GetMapping
    public Collection<Film> getAll() {
        log.info("Got films");

        return films.values();
    }
}
