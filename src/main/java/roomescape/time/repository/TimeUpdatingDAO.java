package roomescape.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.model.Time;

import java.sql.PreparedStatement;

@Repository
public class TimeUpdatingDAO {

    private JdbcTemplate jdbcTemplate;

    public TimeUpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertWithKeyHolder(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into time (time) values (?)",
                    new String[]{"id"}
            );
            ps.setObject(1, time.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Time time) {
        String sql = "delete from time where id = ?";
        jdbcTemplate.update(sql, time.getId());
    }
}
