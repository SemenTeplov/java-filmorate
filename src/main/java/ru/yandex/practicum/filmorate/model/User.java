package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotNull(message = "id is null")
    private int id;

    @NotNull(message = "Email is null")
    @NotBlank(message = "Email is empty")
    @Email(message = "Email isn't correct")
    private String email;

    @NotNull(message = "Login is null")
    @NotBlank(message = "Login is empty")
    private String login;

    private String name;

    @NotNull(message = "Birthday is null")
    @Past(message = "Date is have to past time")
    private LocalDate birthday;
}
