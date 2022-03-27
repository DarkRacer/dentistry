package model.enumeration;

public enum UserType {
    CLIENT(1),
    DOCTOR(2),
    ADMINISTRATOR(3),
    OWNER(4);

    private final int id;

    UserType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
