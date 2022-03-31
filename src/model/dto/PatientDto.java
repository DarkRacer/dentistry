package model.dto;

import java.time.LocalDate;

public class PatientDto {
    private int id;
    private String fio;
    private LocalDate dateOfBirth;
    private int phone;
    private String email;
    private String allergies;
    private String address;

    public PatientDto() {
    }

    public PatientDto(int id, String fio, LocalDate dateOfBirth, int phone, String email, String allergies, String address) {
        this.id = id;
        this.fio = fio;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.allergies = allergies;
        this.address = address;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
