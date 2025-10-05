package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.RatingFilm;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.Collection;

@Slf4j
@Service
public class RatingService {
    private final RatingStorage ratings;

    @Autowired
    public RatingService(@Qualifier("ratingDbStorage") RatingStorage ratings) {
        this.ratings = ratings;
    }

    public RatingFilm add(RatingFilm rating) {
        log.info("Rating added");
        return ratings.add(rating);
    }

    public RatingFilm remove(RatingFilm rating) {
        log.info("Rating removed");
        return ratings.remove(rating);
    }

    public RatingFilm get(Integer id) {
        log.info("Got rating");
        return ratings.get(id);
    }

    public Collection<RatingFilm> getAll() {
        log.info("Got ratings");
        return ratings.getAll();
    }
}
