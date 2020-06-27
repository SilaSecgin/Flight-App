package com.sila.flight_app.Model;

public class User {
    private String Username;
    private String Password;

    public User() {

    }

    public User(String userName, String userPass) {

        this.setUsername(userName);
        this.setPassword(userPass);
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
