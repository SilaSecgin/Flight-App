package com.sila.flight_app.Model;

public class UserProfile {

    private String username;
    private String userId;
    private String pname;
    private String surname;
    private String mail;
    private String phone;
    private String bloodGroup;

    /// Constructor  ///Bu sayfa yüklendiğinde ilk çalışacak yer
    public UserProfile(String id, String username, String pname, String surname, String mail, String phone, String bloodGroup) {
        this.userId = id;
        this.username = username;
        this.pname = pname;
        this.surname = surname;
        this.mail = mail;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
