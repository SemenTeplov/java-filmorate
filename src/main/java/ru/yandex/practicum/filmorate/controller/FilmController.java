package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    List<Film> films = new ArrayList<>();

    @PostMapping
    public Film add(@Valid @RequestBody Film film) throws ValidationException {
        if (isNotValidation(film)) {
            throw new ValidationException("Not validation");
        }

        log.info("Film added");
        film.setId(films.size() + 1);
        films.add(film);

        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
//        if (isNotValidation(film)) {
//            throw new ValidationException("Not validation");
//        }

        for (Film value : films) {
            if (value.getId() == film.getId()) {
                value.setName(film.getName());
                value.setDescription(film.getDescription());
                value.setReleaseDate(film.getReleaseDate());
                value.setDuration(film.getDuration());

                log.info("Film updated");
                return value;
            }
        }

        throw new ValidationException("Film not found");
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Got films");

        return films;
    }

    private boolean isNotValidation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            log.warn("Release date has less 1985.12.28");

            return true;
        } //else if (!film.getDuration().isPositive()) {
//            log.warn("Duration isn't positive");
//
//            return true;
//        }

        return false;
    }
}
