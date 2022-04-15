package model;

public class Picture {
    private int id;
    private String url;
    private int patientId;

    public Picture() {
    }

    public Picture(int id, String url, int patientId) {
        this.id = id;
        this.url = url;
        this.patientId = patientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
