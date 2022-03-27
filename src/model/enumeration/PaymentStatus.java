package model.enumeration;

public enum PaymentStatus {
    NOT_PAID(1),
    PAID(2);

    private final int id;

    PaymentStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
