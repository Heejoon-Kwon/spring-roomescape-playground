package roomescape.reservation.dto;

import roomescape.reservation.model.Reservation;

public class ReservationResponse {
    public final long id;
    public final String name;
    public final String date;
    public final String time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getStartTime().toLocalDate().toString();
        this.time = reservation.getStartTime().toLocalTime().toString();
    }
}
