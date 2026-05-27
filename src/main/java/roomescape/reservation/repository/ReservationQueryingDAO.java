package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.reservation.model.Reservation;
import roomescape.time.model.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationQueryingDAO {

    private JdbcTemplate jdbcTemplate;

    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        LocalDate date = resultSet.getDate("date").toLocalDate();

        Reservation reservation = new Reservation(name, date);
        reservation.setId(id);

        Long time_id = resultSet.getLong("time_id");
        LocalTime time_value = LocalTime.parse(resultSet.getString("time_value"));

        Time time = new Time(time_value);
        time.setId(time_id);

        reservation.setTime(time);

        return reservation;
    };

    public Optional<Reservation> findReservationById(Long id) {
        String sql =
"""
select 
    r.id, 
    r. name, 
    r. date, 
    t.id as time_id,
    t.name as time_value
from reservation as r inner join time as t on r.time_id = t.id 
where id = ?
""";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reservationRowMapper, id));

    }

    public List<Reservation> findAllReservations() {
        String sql =
"""
select 
    r.id, 
    r.name, 
    r.date,
    t.id as time_id,
    t.time as time_value 
from reservation as r inner join time as t on r.time_id = t.id order by r.id
""";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
