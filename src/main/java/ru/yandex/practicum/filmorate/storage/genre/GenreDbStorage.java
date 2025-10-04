package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.dal.genre.GenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component("genreDbStorage")
public class GenreDbStorage implements GenreStorage {
    private final GenreRepository repository;

    @Autowired
    public GenreDbStorage(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Genre add(Genre genre) {
        return repository.add(genre);
    }

    @Override
    public Genre remove(Genre genre) {
        return repository.remove(genre);
    }

    @Override
    public Genre get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Collection<Genre> getAll() {
        return repository.getAll();
    }
}
