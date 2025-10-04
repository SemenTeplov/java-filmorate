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

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FilmServiceTest {
    static Film film = new Film();

    @Mock
    FilmStorage films;

    @Mock
    UserStorage users;

    @InjectMocks
    FilmService service;

    @BeforeAll
    static void init() {
        film.setId(1);
        film.setName("film");
        film.setDescription("Text");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(45);
    }

    @Test
    public void testAdd() {
        Mockito.when(service.add(film)).thenReturn(film);

        Assertions.assertEquals(film, service.add(film));
    }

    @Test
    public void testUpdate() {
        Mockito.when(service.update(film)).thenReturn(film);

        Assertions.assertEquals(film, service.update(film));
    }

    @Test
    public void testRemove() {
        Mockito.when(service.remove(film)).thenReturn(film);

        Assertions.assertEquals(film, service.remove(film));
    }
}
