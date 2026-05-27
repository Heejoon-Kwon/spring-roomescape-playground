package roomescape.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.common.exception.NoSuchReservationTimeException;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.common.exception.NoSuchElementToDeleteException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.repository.ReservationQueryingDAO;
import roomescape.reservation.repository.ReservationUpdatingDAO;
import roomescape.time.model.Time;
import roomescape.time.repository.TimeQueryingDAO;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationUpdatingDAO reservationUpdatingDAO;
    private final ReservationQueryingDAO reservationQueryingDAO;
    private final TimeQueryingDAO timeQueryingDAO;

    @Autowired
    public ReservationService(ReservationUpdatingDAO reservationUpdatingDAO, ReservationQueryingDAO reservationQueryingDAO, TimeQueryingDAO timeQueryingDAO) {
        this.reservationUpdatingDAO = reservationUpdatingDAO;
        this.reservationQueryingDAO = reservationQueryingDAO;
        this.timeQueryingDAO = timeQueryingDAO;
    }

    public List<ReservationResponse> getReservations() {
        return reservationQueryingDAO.findAllReservations().stream().map(ReservationResponse::from).toList();
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        Reservation reservation = ReservationRequest.toEntityFrom(request);

        Time time = timeQueryingDAO.findTimeByValue(request.time)
                .orElseThrow(() -> new NoSuchReservationTimeException("There is no time with value " + request.time));
        reservation.setTime(time);

        Long id = reservationUpdatingDAO.insertWithKeyHolder(reservation);
        reservation.setId(id);

        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationQueryingDAO.findReservationById(id)
                .orElseThrow(() -> new NoSuchElementToDeleteException("There is no reservation with id " + id));

        reservationUpdatingDAO.delete(reservation);
    }
}
