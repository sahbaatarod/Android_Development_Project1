package com.example.firebaseapp;

public class UserModel {
    private String UID;
    private String email,fname,lname,role;
    public UserModel() {

    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLname() {
        return lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }
}
