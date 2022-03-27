package model;

import java.util.Date;

public class CallLog {
    private int     id;
    private String  complaints;
    private Date    dateOfRequest;
    private int     idPatient;

    public CallLog() {
    }

    public CallLog(int id, String complaints, Date dateOfRequest, int idPatient) {
        this.id = id;
        this.complaints = complaints;
        this.dateOfRequest = dateOfRequest;
        this.idPatient = idPatient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }
}
