package model.dto;

public class ComplaintsDto {
    private int id;
    private String fio;
    private String info;
    private int complId;

    public ComplaintsDto() {
    }

    public ComplaintsDto(int id, String fio, String info, int complId) {
        this.id = id;
        this.fio = fio;
        this.info = info;
        this.complId = complId;
    }

    public ComplaintsDto(int id, String fio, String info) {
        this.id = id;
        this.fio = fio;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getComplId() {
        return complId;
    }

    public void setComplId(int complId) {
        this.complId = complId;
    }
}
