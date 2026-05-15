package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.InvalidDateOrTimeFormatException;
import roomescape.exception.NoSuchElementToDeleteException;
import roomescape.exception.OverlappedReservationsException;
import roomescape.exception.RequestParameterMissingException;
import roomescape.model.Reservation;
import roomescape.repository.QueryingDAO;
import roomescape.repository.UpdatingDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ReservationService {

    private final UpdatingDAO updatingDAO;
    private final QueryingDAO queryingDAO;

    public ReservationService(UpdatingDAO updatingDAO, QueryingDAO queryingDAO) {
        this.updatingDAO = updatingDAO;
        this.queryingDAO = queryingDAO;
    }

    public List<ReservationResponse> getReservations() {
        return queryingDAO.findAllReservations().stream().map(ReservationResponse::new).toList();
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

        if (queryingDAO.existsOverlappingReservation(reservation)) {
            throw new OverlappedReservationsException("Reservations overlap");
        }

        Long id = updatingDAO.insertWithKeyHolder(reservation);
        reservation.setId(id);

        return new ReservationResponse(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = queryingDAO.findReservationById(id)
                .orElseThrow(() -> new NoSuchElementToDeleteException("There is no reservation with id " + id));

        updatingDAO.delete(reservation);
    }

    private String getRequiredValue(String value, String parameterName) {
        if (value == null || value.isBlank()) {
            throw new RequestParameterMissingException(parameterName);
        }
        return value;
    }
}
