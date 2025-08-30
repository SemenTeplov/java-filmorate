package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.ValidReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @ValidReleaseDate
    private LocalDate releaseDate;

    @Min(value = 1, message = "Duration isn't positive")
    private int duration;

    @Setter(AccessLevel.NONE)
    private final Set<Integer> likes = new HashSet<>();

    public void addLike(Integer id) {
        likes.add(id);
    }

    public void removeLike(Integer id) {
        likes.remove(id);
    }
}
