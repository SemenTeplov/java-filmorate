package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreDbStorage.class})
public class GenreDBTests {
    private final GenreDbStorage storage;
    private static Genre genre;

    @BeforeAll
    public static void init() {
        genre = new Genre();

        genre.setId(1);
        genre.setName("Comedy");
    }

    @Test
    public void testAdd() {
        Assertions.assertThat(storage.add(genre)).hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    public void testRemove() {
        storage.add(genre);

        Assertions.assertThat(storage.remove(genre)).hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    public void testGet() {
        storage.add(genre);

        Assertions.assertThat(storage.get(1)).hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    public void testGetAll() {
        storage.add(genre);

        Assertions.assertThat(storage.getAll().toArray()[0]).hasFieldOrPropertyWithValue("id", 1);
    }
}
