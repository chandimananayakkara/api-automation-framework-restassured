package com.api.framework.models;

public class User {

    public String name;
    public String email;
    public String gender;
    public String status;

    public User() {
    }

    public User(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }
}
