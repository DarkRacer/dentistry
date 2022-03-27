package model;

import model.enumeration.UserType;

public class User {
    private int         id;
    private String      login;
    private String      password;
    private UserType    type;

    public User() {
    }

    public User(int id, String login, String password, UserType type) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
