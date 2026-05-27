package roomescape.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.time.model.Time;

import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeQueryingDAO {

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time();

        time.setId(resultSet.getLong("id"));
        time.setTime(LocalTime.parse(resultSet.getString("time")));

        return time;
    };

    public TimeQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time findTimeById(Long id) {
        String sql = "select * from time where id = ?";

        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }

    public List<Time> findAllTime() {
        String sql = "select * from time";
        List<Time> timeList = jdbcTemplate.query(sql, timeRowMapper);

        return timeList;
    }
}
