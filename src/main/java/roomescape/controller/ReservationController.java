package roomescape.controller;

<<<<<<< main
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.model.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;
=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain_model.Reservation;
import roomescape.domain_model.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
>>>>>>> heejoon-kwon

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
<<<<<<< main

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> showReservations() {
        return reservationService.getResevations();
=======
    Reservations reservations = new Reservations();

    private AtomicLong id = new AtomicLong(1);

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservations.getReservations().stream().map(ReservationResponse::new).toList();
>>>>>>> heejoon-kwon
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
<<<<<<< main
        ReservationResponse response = reservationService.addReservation(request);
=======
        Reservation reservation = new Reservation(id.getAndIncrement(), request.name, request.date, request.time);
        reservations.addReservation(reservation);
        ReservationResponse response = new ReservationResponse(reservation);
>>>>>>> heejoon-kwon

        return ResponseEntity
                .created(URI.create("/reservations/" + response.id))
                .body(response);
    }

    @DeleteMapping("/{id}")
<<<<<<< main
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
=======
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservations.deleteReservation(id);
>>>>>>> heejoon-kwon

        return ResponseEntity.noContent().build();
    }
}
