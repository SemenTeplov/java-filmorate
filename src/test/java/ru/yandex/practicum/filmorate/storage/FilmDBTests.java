package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import org.assertj.core.api.Assertions;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class})
class FilmDBTests {
	private final FilmDbStorage filmDbStorage;
	private static Film film;

	@BeforeAll
	public static void init() {
		film = new Film();

		film.setName("film");
		film.setDescription("some text");
		film.setReleaseDate(LocalDate.now());
		film.setMpa("G");
	}

	@Test
	public void testAdd() {
		Assertions.assertThat(filmDbStorage.add(film)).hasFieldOrPropertyWithValue("id", 1);
	}

	@Test
	public void testRemove() {
		Assertions.assertThat(filmDbStorage.remove(film)).hasFieldOrPropertyWithValue("id", 2);
	}

	@Test
	public void testUpdate() {
		filmDbStorage.add(film);
		film.setName("change");

		Assertions.assertThat(filmDbStorage.update(film)).hasFieldOrPropertyWithValue("name", "change");
	}

	@Test
	public void testGetAll() {
		filmDbStorage.add(film);

		Assertions.assertThat(filmDbStorage.getAll().toArray()[0]).hasFieldOrPropertyWithValue("id", 2);
	}
}
