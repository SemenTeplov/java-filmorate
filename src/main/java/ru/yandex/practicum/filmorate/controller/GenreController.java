package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService service;

    public GenreController(@RequestBody GenreService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Genre add(@RequestBody Genre genre) {
        return service.add(genre);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Genre remove(@RequestBody Genre genre) {
        return service.remove(genre);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre get(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Genre> getAll() {
        return service.getAll();
    }
}
