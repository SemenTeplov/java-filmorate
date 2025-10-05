package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingFilm {
    @Id
    private Integer id;

    @NotNull(message = "Name is null")
    @NotBlank(message = "Name is empty")
    private String name;
}
