package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.RequestParameterMissingException;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private final Reservations reservations = new Reservations();

    private final AtomicLong id = new AtomicLong(1);

    public List<ReservationResponse> getResevations() {
        return reservations.getReservations().stream().map(ReservationResponse::new).toList();
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        String name = getRequiredValue(request.name, "name");
        String date = getRequiredValue(request.date, "date");
        String time = getRequiredValue(request.time, "time");

        Reservation reservation = new Reservation(
                id.getAndIncrement(),
                name,
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
        reservations.addReservation(reservation);

        return new ReservationResponse(reservation);
    }

    public void deleteReservation(Long id) {
        reservations.deleteReservation(id);
    }

    private String getRequiredValue(String value, String parameterName) {
        if (value == null || value.isBlank()) {
            throw new RequestParameterMissingException(parameterName);
        }
        return value;
    }
}
