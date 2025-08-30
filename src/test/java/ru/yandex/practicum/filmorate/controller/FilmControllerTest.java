package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Add film code: 201")
    public void addFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\":1,\"name\":\"movie\",\"description\":\"description\",\"releaseDate\":\"1990-12-12\", \"duration\":\"45\"}"))
                        .andReturn();

        Assertions.assertEquals(201, response.getResponse().getStatus());
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
    @DisplayName("Release date  isn't correct code: 400")
    public void addNotCorrectReleaseDateFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\":1,\"name\":\"movie\",\"description\":\"description\",\"releaseDate\":\"1895-11-28\", \"duration\":\"45\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [releaseDate]"));
        Assertions.assertTrue(message.contains("default message [Release date has less 1895.12.28]"));
    }

    @Test
    @DisplayName("Duration isn't correct code: 400")
    public void addNotCorrectDurationFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\":1,\"name\":\"movie\",\"description\":\"description\",\"releaseDate\":\"1990-12-12\", \"duration\":\"-45\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [duration]"));
        Assertions.assertTrue(message.contains("default message [Duration isn't positive]"));
    }

    @Test
    @DisplayName("Add like code: 201")
    public void addLike() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\":1,\"name\":\"movie\",\"description\":\"description\",\"releaseDate\":\"1990-12-12\", \"duration\":\"45\"}"));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/films/1/like/1")).andReturn();

        Assertions.assertEquals(201, response.getResponse().getStatus());
    }

    @Test
    @DisplayName("Add like to don't exist film code: 404")
    public void addLikeWrong() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/films/1/like/1")).andReturn();

        Assertions.assertEquals(404, response.getResponse().getStatus());
    }

    @Test
    @DisplayName("Remove like code: 200")
    public void removeLike() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1,\"name\":\"movie\",\"description\":\"description\",\"releaseDate\":\"1990-12-12\", \"duration\":\"45\"}"));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete("/films/1/like/1")).andReturn();

        Assertions.assertEquals(200, response.getResponse().getStatus());
    }

    @Test
    @DisplayName("Remove like from don't exist film code: 404")
    public void removeLikeWrong() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete("/films/1/like/1")).andReturn();

        Assertions.assertEquals(404, response.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get all films: 200")
    public void getAllFilms() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/films/popular?count=1000")).andReturn();

        Assertions.assertEquals(200, response.getResponse().getStatus());
    }
}