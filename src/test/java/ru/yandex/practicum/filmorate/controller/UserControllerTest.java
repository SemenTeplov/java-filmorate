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
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    static UserController userController;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public static void init() {
        userController = new UserController();
    }

    @Test
    @DisplayName("Not email code: 400")
    public void AddWithoutEmailFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1,\"email\":\"\",\"login\":\"Login\",\"name\":\"Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [email]"));
        Assertions.assertTrue(message.contains("default message [Email is empty]"));
    }

    @Test
    @DisplayName("Not correct email code: 400")
    public void AddNotCorrectEmailFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\":1,\"email\":\"email@\",\"login\":\"Login\",\"name\":\"Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [email]"));
        Assertions.assertTrue(message.contains("default message [Email isn't correct]"));
    }

    @Test
    @DisplayName("Not login code: 400")
    public void AddNotLoginFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\":1,\"email\":\"email@\",\"login\":\"\",\"name\":\"Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [login]"));
        Assertions.assertTrue(message.contains("default message [Login is empty]"));
    }

    @Test
    public void changeNameOnLogin() {
        User user = new User(1, "email@.com", "Login", "", LocalDate.of(1980, 12, 12));
        User result = userController.create(user);

        Assertions.assertEquals(result.getName(), result.getLogin());
    }

    @Test
    @DisplayName("Not correct birthday code: 400")
    public void AddNotCorrectBirthdayFilm() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\":1,\"email\":\"email@\",\"login\":\"Login\",\"name\":\"Name\",\"birthday\":\"" + LocalDate.now() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String message = response.getResolvedException().getMessage();

        Assertions.assertTrue(message.contains("default message [birthday]"));
        Assertions.assertTrue(message.contains("default message [Date is have to past time]"));
    }
}