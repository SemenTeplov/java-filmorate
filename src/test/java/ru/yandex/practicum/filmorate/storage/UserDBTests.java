package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class})
public class UserDBTests {
    private final UserDbStorage userDbStorage;
    private static User user;

    @BeforeAll
    public static void init() {
        user = new User();

        user.setId(2);
        user.setName("user");
        user.setEmail("email@mail.com");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2000, 2, 2));
    }

    @Test
    public void testAdd() {
        Assertions.assertThat(userDbStorage.add(user)).hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    public void testRemove() {
        userDbStorage.add(user);

        Assertions.assertThat(userDbStorage.remove(user)).hasFieldOrPropertyWithValue("id", 3);
    }

    @Test
    public void testUpdate() {
        userDbStorage.add(user);

        user.setName("change");

        Assertions.assertThat(userDbStorage.update(user)).hasFieldOrPropertyWithValue("name", "change");
    }

    @Test
    public void testGetAll() {
        userDbStorage.add(user);

        Assertions.assertThat(userDbStorage.getAll().toArray()[0]).hasFieldOrPropertyWithValue("id", 2);
    }
}
