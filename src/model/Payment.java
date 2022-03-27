package model;

import model.enumeration.PaymentStatus;

public class Payment {
    private int             id;
    private int             sum;
    private PaymentStatus   status;
    private int             idRecord;

    public Payment() {
    }

    public Payment(int id, int sum, PaymentStatus status, int idRecord) {
        this.id = id;
        this.sum = sum;
        this.status = status;
        this.idRecord = idRecord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }
}
