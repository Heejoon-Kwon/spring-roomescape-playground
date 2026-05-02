package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain_model.Reservation;
import roomescape.domain_model.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    Reservations reservations = new Reservations();

    private AtomicLong id = new AtomicLong(1);

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservations.getReservations().stream().map(ReservationResponse::new).toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = new Reservation(id.getAndIncrement(), request.name, request.date, request.time);
        reservations.addReservation(reservation);
        ReservationResponse response = new ReservationResponse(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + response.id))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservations.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
