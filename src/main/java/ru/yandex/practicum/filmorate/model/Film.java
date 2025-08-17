package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private int id;

    @NotNull(message = "Name is null")
    @NotBlank(message = "Name is empty")
    private String name;

    @NotNull(message = "Description is null")
    @Length(max = 200, message = "Length of description more 200")
    private String description;

    private LocalDate releaseDate;
    private Duration duration;
}
