package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class UpdatingDAO {

    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        return reservation;
    };

    public void insert(Reservation reservation) {
        String sql = "insert into reservation(name, date, time) values(?, ?, ?)";
        jdbcTemplate.update(
                sql,
                reservation.getName(),
                reservation.getStartTime().toLocalDate(),
                reservation.getStartTime().toLocalTime()
        );
    }
}
