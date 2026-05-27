package roomescape.reservation.dto;

import roomescape.reservation.model.Reservation;
import java.time.LocalDate;

public class ReservationRequest {
    public String name;
    public String date;
    public String time;

    public static Reservation toEntityFrom(ReservationRequest request) {
        return new Reservation(
                request.name,
                LocalDate.parse(request.date)
        );
    }
}
