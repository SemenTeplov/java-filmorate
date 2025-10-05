package ru.yandex.practicum.filmorate.dal.rating;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.RatingFilm;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RatingRowMapper implements RowMapper<RatingFilm> {
    @Override
    public RatingFilm mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        RatingFilm rating = new RatingFilm();

        rating.setId(resultSet.getInt("id"));
        rating.setName(resultSet.getString("name"));

        return rating;
    }
}
