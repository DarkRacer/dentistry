package model.dto;

public class DoctorDto {
    private int id;
    private String fio;
    private String specialization;
    private String phone;
    private String email;

    public DoctorDto() {
    }

    public DoctorDto(int id, String fio, String specialization, String phone, String email) {
        this.id = id;
        this.fio = fio;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
