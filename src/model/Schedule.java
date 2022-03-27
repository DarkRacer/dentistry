package model;

import java.sql.Time;
import java.util.Date;

public class Schedule {
    private int     id;
    private Date    date;
    private Time    time;
    private int     idDoc;
    private int     idParent;

    public Schedule() {
    }

    public Schedule(int id, Date date, Time time, int idDoc, int idParent) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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
