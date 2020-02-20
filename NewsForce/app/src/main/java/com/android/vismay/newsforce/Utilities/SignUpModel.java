package com.android.vismay.newsforce.Utilities;

public class SignUpModel {
    String email,name,phone,username,notify;
    public SignUpModel(String email, String name, String phone, String username, String notify) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.notify = notify;
    }

    public SignUpModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }
}
