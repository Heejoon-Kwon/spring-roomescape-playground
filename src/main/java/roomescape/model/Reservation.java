package roomescape.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Reservation {

    public static final int AVAILABLE_HOURS = 2;

    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.startTime = LocalDateTime.of(date, time);
        this.endTime = startTime.plusHours(AVAILABLE_HOURS);
    }
}
