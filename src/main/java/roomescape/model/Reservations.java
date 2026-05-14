package roomescape.model;

import roomescape.exception.NoSuchElementToDeleteException;
import roomescape.exception.OverlappedReservationsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        checkOverlap(reservation);
        reservations.add(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation target = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementToDeleteException("No such a reservation with id "+id));

        reservations.remove(target);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    private void checkOverlap(Reservation newReservation) {
        for (Reservation reservation : reservations) {
            if (isOverlapped(reservation, newReservation)) {
                throw new OverlappedReservationsException("Reservations overlap");
            }
        }
    }

    private boolean isOverlapped(Reservation existingOne, Reservation newOne) {
        return newOne.getStartTime().isBefore(existingOne.getEndTime())
                && newOne.getEndTime().isAfter(existingOne.getStartTime());
    }
}
