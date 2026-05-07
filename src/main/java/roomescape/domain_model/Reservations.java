package roomescape.domain_model;

import java.util.ArrayList;
import java.util.List;

public class Reservations {
    List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteReservation(long id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
