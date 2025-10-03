package ru.yandex.practicum.filmorate.storage.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.dal.rating.RatingRepository;
import ru.yandex.practicum.filmorate.model.RatingFilm;

import java.util.Collection;

@Component("ratingDbStorage")
public class RatingDbStorage implements RatingStorage {
    private final RatingRepository repository;

    @Autowired
    public RatingDbStorage(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public RatingFilm add(RatingFilm rating) {
        return repository.add(rating);
    }

    @Override
    public RatingFilm remove(RatingFilm rating) {
        return repository.remove(rating);
    }

    @Override
    public RatingFilm get(Integer id) {
        return repository.get(id);
    }

    @Override
    public Collection<RatingFilm> getAll() {
        return repository.getAll();
    }
}
