package roomescape.time.model;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class Time {

    private Long  id;
    private LocalTime time;

    public Time() {}

    public Time(LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(LocalTime time) throws IllegalArgumentException {
        this.time = time;
    }
}
