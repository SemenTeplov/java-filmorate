package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.dal.film.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Slf4j
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final FilmRepository repository;

    @Autowired
    public FilmDbStorage(FilmRepository repository) {
        this.repository = repository;
    }

    @Override
    public Film add(Film film) {
        return repository.add(film);
    }

    @Override
    public Film remove(Film film) {
        return repository.remove(film);
    }

    @Override
    public Film update(Film film) {
        return repository.update(film);
    }

    @Override
    public Film get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Collection<Film> getAll() {
        return repository.getAll();
    }
}
