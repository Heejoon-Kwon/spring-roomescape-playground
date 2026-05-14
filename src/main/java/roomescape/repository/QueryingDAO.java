package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public class QueryingDAO {

    private JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation();

        reservation.setId(resultSet.getLong("id"));
        reservation.setName(resultSet.getString("name"));
        reservation.setDateAndTime(resultSet.getDate("date").toLocalDate(), resultSet.getTime("time").toLocalTime());

        return reservation;
    };

    public Optional<Reservation> findReservationById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, actorRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation order by id";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public boolean existsOverlappingReservation(Reservation newReservation) {
        return findAllReservations().stream()
                .filter(existingReservation -> !isSameReservation(existingReservation, newReservation))
                .anyMatch(existingReservation -> isOverlapped(existingReservation, newReservation));
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
