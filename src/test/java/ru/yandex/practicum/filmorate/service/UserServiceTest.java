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

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    static User user = new User();

    @Mock
    UserStorage users;

    @InjectMocks
    UserService service;

    @BeforeAll
    static void init() {
        user.setId(1);
        user.setName("User");
        user.setEmail("email@mail.com");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(1990, 1, 1));
    }

    @Test
    public void createTest() {
        Mockito.when(service.create(user)).thenReturn(user);

        Assertions.assertEquals(user, service.create(user));
    }

    @Test
    public void updateTest() {
        Mockito.when(service.update(user)).thenReturn(user);

        Assertions.assertEquals(user, service.update(user));
    }

    @Test
    public void removeTest() {
        Mockito.when(service.remove(user)).thenReturn(user);

        Assertions.assertEquals(user, service.remove(user));
    }
}
