package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private int     id;
    private LocalDate date;
    private LocalTime time;
    private int     idDoc;
    private int     idParent;

    public Schedule() {
    }

    public Schedule(int id, LocalDate date, LocalTime time, int idDoc, int idParent) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.idDoc = idDoc;
        this.idParent = idParent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }
}
