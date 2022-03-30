package client.record;

import java.time.LocalDate;
import java.time.LocalTime;

public class RecordDto {
    private String doctor;
    private LocalDate date;
    private LocalTime  time;

    public RecordDto() {
    }

    public RecordDto(String doctor, LocalDate date, LocalTime time) {
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime  getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
