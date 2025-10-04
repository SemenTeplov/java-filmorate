package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;

@Slf4j
@Service
public class GenreService {
    private final GenreStorage genres;

    @Autowired
    public GenreService(@Qualifier("genreDbStorage") GenreStorage genres) {
        this.genres = genres;
    }

    public Genre add(Genre genre) {
        log.info("Genre added");
        return genres.add(genre);
    }

    public Genre remove(Genre genre) {
        log.info("Genre removed");
        return genres.remove(genre);
    }

    public Genre get(Integer id) {
        log.info("Got genre");
        return genres.get(id);
    }

    public Collection<Genre> getAll() {
        log.info("Got genres");
        return genres.getAll();
    }
}
