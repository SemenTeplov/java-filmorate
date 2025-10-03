package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.RatingFilm;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
public class RatingController {
    private final RatingService service;

    public RatingController(@RequestBody RatingService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingFilm add(@RequestBody RatingFilm rating) {
        return service.add(rating);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public RatingFilm remove(@RequestBody RatingFilm rating) {
        return service.remove(rating);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RatingFilm get(@RequestBody Integer id) {
        return service.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<RatingFilm> getAll() {
        return service.getAll();
    }
}
