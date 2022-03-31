package model.dto;

import java.time.LocalDate;

public class PaymentDto {
    private int id;
    private String doctor;
    private LocalDate date;
    private String  status;
    private int price;

    public PaymentDto() {
    }

    public PaymentDto(int id, String doctor, LocalDate date, String status, int price) {
        this.id = id;
        this.doctor = doctor;
        this.date = date;
        this.status = status;
        this.price = price;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
