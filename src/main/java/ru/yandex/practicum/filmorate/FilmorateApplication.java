package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@SpringBootApplication
public class FilmorateApplication {
	public static void main(String[] args) throws ValidationException {
		SpringApplication.run(FilmorateApplication.class, args);
	}

}
