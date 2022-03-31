package model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RecordDto {
    private int id;
    private String fio;
    private LocalDate date;
    private LocalTime  time;

    public RecordDto() {
    }

    public RecordDto(String fio, LocalDate date, LocalTime time) {
        this.fio = fio;
        this.date = date;
        this.time = time;
    }

    public RecordDto(int id, String fio, LocalDate date, LocalTime time) {
        this.id = id;
        this.fio = fio;
        this.date = date;
        this.time = time;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
