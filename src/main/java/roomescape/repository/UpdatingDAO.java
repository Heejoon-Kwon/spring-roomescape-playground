package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.sql.PreparedStatement;

@Repository
public class UpdatingDAO {

    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Reservation reservation) {
        String sql = "insert into reservation(name, date, time) values(?, ?, ?)";
        jdbcTemplate.update(
                sql,
                reservation.getName(),
                reservation.getStartTime().toLocalDate(),
                reservation.getStartTime().toLocalTime()
        );
    }

    public Long insertWithKeyHolder(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getStartTime().toLocalDate());
            ps.setObject(3, reservation.getStartTime().toLocalTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int delete(Reservation reservation) {
        String sql = "delete from reservation where id = ?";
        Long id = reservation.getId();
        return jdbcTemplate.update(sql, id);
    }
}
