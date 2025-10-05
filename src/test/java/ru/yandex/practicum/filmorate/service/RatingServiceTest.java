package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.yandex.practicum.filmorate.model.RatingFilm;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RatingServiceTest {
    static RatingFilm rating = new RatingFilm();

    @Mock
    RatingStorage ratings;

    @InjectMocks
    RatingService service;

    @BeforeAll
    static void init() {
        rating.setId(1);
        rating.setName("G");
    }

    @Test
    public void addTest() {
        Mockito.when(service.add(rating)).thenReturn(rating);

        Assertions.assertEquals(rating, service.add(rating));
    }

    @Test
    public void removeTest() {
        Mockito.when(service.remove(rating)).thenReturn(rating);

        Assertions.assertEquals(rating, service.remove(rating));
    }

    @Test
    public void getTest() {
        Mockito.when(service.get(1)).thenReturn(rating);

        Assertions.assertEquals(rating, service.get(1));
    }
}
