package roomescape.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation {

    public static final int AVAILABLE_HOURS = 2;

    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Reservation() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAndTime(LocalDate date, LocalTime time) {
        this.startTime = LocalDateTime.of(date, time);
        this.endTime = startTime.plusHours(AVAILABLE_HOURS);
    }
}
