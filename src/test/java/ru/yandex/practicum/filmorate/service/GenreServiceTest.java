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

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GenreServiceTest {
    static Genre genre = new Genre();

    @Mock
    GenreStorage genres;

    @InjectMocks
    GenreService service;

    @BeforeAll
    static void init() {
        genre.setId(1);
        genre.setName("Comedy");
    }

    @Test
    public void addTest() {
        Mockito.when(service.add(genre)).thenReturn(genre);

        Assertions.assertEquals(genre, service.add(genre));
    }

    @Test
    public void removeTest() {
        Mockito.when(service.remove(genre)).thenReturn(genre);

        Assertions.assertEquals(genre, service.remove(genre));
    }

    @Test
    public void getTest() {
        Mockito.when(service.get(1)).thenReturn(genre);

        Assertions.assertEquals(genre, service.get(1));
    }
}
