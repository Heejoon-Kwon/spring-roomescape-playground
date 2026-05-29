package roomescape.reservation.dto;

import roomescape.reservation.model.Reservation;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReservationRequest {
    public String name;
    public String date;
    public String time;

    public static Reservation toEntityFrom(ReservationRequest request) throws DateTimeParseException {
        return new Reservation(
                request.name,
                LocalDate.parse(request.date)
        );
    }
}