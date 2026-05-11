package roomescape.dto;

public class ReservationRequest {
    public final String name;
    public final String date;
    public final String time;

    public ReservationRequest(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
