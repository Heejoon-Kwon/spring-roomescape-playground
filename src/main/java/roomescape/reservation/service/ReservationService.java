package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.common.exception.InvalidDateOrTimeFormatException;
import roomescape.common.exception.NoSuchElementToDeleteException;
import roomescape.common.exception.OverlappedReservationsException;
import roomescape.common.exception.RequestParameterMissingException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.repository.ReservationQueryingDAO;
import roomescape.reservation.repository.ReservationUpdatingDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationUpdatingDAO reservationUpdatingDAO;
    private final ReservationQueryingDAO reservationQueryingDAO;

    public ReservationService(ReservationUpdatingDAO reservationUpdatingDAO, ReservationQueryingDAO reservationQueryingDAO) {
        this.reservationUpdatingDAO = reservationUpdatingDAO;
        this.reservationQueryingDAO = reservationQueryingDAO;
    }

    public List<ReservationResponse> getReservations() {
        return reservationQueryingDAO.findAllReservations().stream().map(ReservationResponse::new).toList();
    }

    public ReservationResponse addReservation(ReservationRequest request) throws InvalidDateOrTimeFormatException {
        String name = getRequiredValue(request.name, "name");
        String date = getRequiredValue(request.date, "date");
        String time = getRequiredValue(request.time, "time");

        Reservation reservation = new Reservation();

        reservation.setName(name);
        try {
            reservation.setDateAndTime(LocalDate.parse(date), LocalTime.parse(time));
        } catch (DateTimeParseException e) {
            throw new InvalidDateOrTimeFormatException("Date or Time has invalid format.");
        }

        if (reservationQueryingDAO.existsOverlappingReservation(reservation)) {
            throw new OverlappedReservationsException("Reservations overlap");
        }

        Long id = reservationUpdatingDAO.insertWithKeyHolder(reservation);
        reservation.setId(id);

        return new ReservationResponse(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationQueryingDAO.findReservationById(id)
                .orElseThrow(() -> new NoSuchElementToDeleteException("There is no reservation with id " + id));

        reservationUpdatingDAO.delete(reservation);
    }

    private String getRequiredValue(String value, String parameterName) {
        if (value == null || value.isBlank()) {
            throw new RequestParameterMissingException(parameterName);
        }
        return value;
    }
}
