package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.controller.ReservationController;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidDateOrTimeFormatException;
import roomescape.exception.NoSuchElementToDeleteException;
import roomescape.exception.OverlappedReservationsException;
import roomescape.exception.RequestParameterMissingException;
import roomescape.service.ReservationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ExceptionHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void missingParameterExceptionCreatesBadRequestResponse() throws Exception {
        given(reservationService.addReservation(any(ReservationRequest.class)))
                .willThrow(new RequestParameterMissingException("name"));

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson("", "2023-08-05", "15:40")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("name is missing."))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void invalidDateOrTimeExceptionCreatesBadRequestResponse() throws Exception {
        given(reservationService.addReservation(any(ReservationRequest.class)))
                .willThrow(new InvalidDateOrTimeFormatException("Date or Time has invalid format."));

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson("Brown", "invalid-date", "15:40")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Date or Time has invalid format."))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void overlappedReservationsExceptionCreatesBadRequestResponse() throws Exception {
        given(reservationService.addReservation(any(ReservationRequest.class)))
                .willThrow(new OverlappedReservationsException("Reservations overlap"));

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson("Brown", "2023-08-05", "15:40")))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Reservations overlap"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void noSuchElementToDeleteExceptionCreatesNotFoundResponse() throws Exception {
        willThrow(new NoSuchElementToDeleteException("No such a reservation with id 999"))
                .given(reservationService)
                .deleteReservation(999L);

        mockMvc.perform(delete("/reservations/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("No such a reservation with id 999"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private static String reservationJson(String name, String date, String time) {
        return "{"
                + "\"name\":\"" + name + "\","
                + "\"date\":\"" + date + "\","
                + "\"time\":\"" + time + "\""
                + "}";
    }
}
