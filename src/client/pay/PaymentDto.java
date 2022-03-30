package client.pay;

import java.time.LocalDate;

public class PaymentDto {
    private String doctor;
    private LocalDate date;
    private String  status;
    private int price;

    public PaymentDto() {
    }

    public PaymentDto(String doctor, LocalDate date, String status, int price) {
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
}
