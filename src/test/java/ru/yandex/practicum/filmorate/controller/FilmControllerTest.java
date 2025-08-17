package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {
    static FilmController filmController;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public static void init() {
        filmController = new FilmController();
    }

    @Test
    public void addFilm() throws Exception {
        Film film = new Film(1, "Movie", "Something movie",
                LocalDate.of(2011, 3, 15), Duration.ofMinutes(45));
        Film other = filmController.add(film);

        Assertions.assertEquals(film, other);
    }

    @Test
    @DisplayName("Not name code: 400")
    public void notCorrectNameAddFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/films")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1,\"name\":\"\",\"description\":\"Something movie\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [name]"));
        Assertions.assertTrue(message.contains("default message [Name is empty]"));
    }

    @Test
    @DisplayName("Length more 200 code: 400")
    public void notCorrectDescriptionAddFilm() throws Exception {
        String string = "description".repeat(20);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/films")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1,\"name\":\"movie\",\"description\":\"" + string + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [description]"));
        Assertions.assertTrue(message.contains("default message [Length of description more 200]"));
    }

    @Test
    public void addNotCorrectDateFilm() {
        Film film = new Film(1, "Movie", "Something movie",
                LocalDate.of(1985, 11, 28), Duration.ofMinutes(45));

        Assertions.assertThrows(Exception.class, () -> filmController.add(film));
    }

    @Test
    public void addNotCorrectDurationFilm() {
        Film film = new Film(1, "Movie", "Something movie",
                LocalDate.of(1999, 11, 28), Duration.ofMinutes(-45));

        Assertions.assertThrows(Exception.class, () -> filmController.add(film));
    }
}