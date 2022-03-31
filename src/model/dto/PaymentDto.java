package model.dto;

import java.time.LocalDate;

public class PaymentDto {
    private int id;
    private String fio;
    private LocalDate date;
    private String  status;
    private int price;

    public PaymentDto() {
    }

    public PaymentDto(int id, String fio, LocalDate date, String status, int price) {
        this.id = id;
        this.fio = fio;
        this.date = date;
        this.status = status;
        this.price = price;
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
