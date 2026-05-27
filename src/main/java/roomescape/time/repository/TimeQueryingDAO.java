package roomescape.time.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.time.model.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeQueryingDAO {

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Long id = resultSet.getLong("id");
        LocalTime time_value = LocalTime.parse(resultSet.getString("time"));

        Time time = new Time(time_value);
        time.setId(id);

        return time;
    };

    public TimeQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Time> findTimeById(Long id) throws EmptyResultDataAccessException {
        String sql = "select * from time where id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, timeRowMapper, id));
    }

    public Optional<Time> findTimeByValue(String value) throws EmptyResultDataAccessException {
        String sql = "select * from time where time = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, timeRowMapper, value));
    }

    public List<Time> findAllTime() {
        String sql = "select * from time";
        List<Time> timeList = jdbcTemplate.query(sql, timeRowMapper);

        return timeList;
    }
}
