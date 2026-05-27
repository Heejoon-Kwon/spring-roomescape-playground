package roomescape.time.dto;

import roomescape.time.model.Time;

import java.time.LocalTime;

public class TimeCreationRequest {
    public String time;

    public static Time toEntity(TimeCreationRequest request) {
        Time time = new Time();
        time.setTime(LocalTime.parse(request.time));

        return time;
    }
}
