package roomescape.dto;

import roomescape.domain_model.Reservation;

public class ReservationResponse {
    public long id;
    public String name;
    public String date;
    public String time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }
}
