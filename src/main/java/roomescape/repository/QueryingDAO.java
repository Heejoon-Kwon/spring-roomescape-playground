package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class QueryingDAO {

    private JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation();

        reservation.setId(resultSet.getLong("id"));
        reservation.setName(resultSet.getString("name"));
        reservation.setDateAndTime(resultSet.getDate("date").toLocalDate(), resultSet.getTime("time").toLocalTime());

        return reservation;
    };

    public Optional<Reservation> findReservationById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reservationRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation order by id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public boolean existsOverlappingReservation(Reservation newReservation) {
        LocalDate date = newReservation.getStartTime().toLocalDate();
        LocalTime time = newReservation.getStartTime().toLocalTime();

        String sql = """
                SELECT count(*)
                FROM reservation
                WHERE date = ?
                AND time > ?
                AND time < ?
                """;

        LocalTime startTimeBound = time.minusHours(Reservation.AVAILABLE_HOURS);
        LocalTime endTimeBound = time.plusHours(Reservation.AVAILABLE_HOURS);

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, startTimeBound, endTimeBound);

        return count > 0;
    }

    private boolean isSameReservation(Reservation existingReservation, Reservation newReservation) {
        return newReservation.getId() != null
                && newReservation.getId().equals(existingReservation.getId());
    }

    private boolean isOverlapped(Reservation existingReservation, Reservation newReservation) {
        return newReservation.getStartTime().isBefore(existingReservation.getEndTime())
                && newReservation.getEndTime().isAfter(existingReservation.getStartTime());
    }
}
