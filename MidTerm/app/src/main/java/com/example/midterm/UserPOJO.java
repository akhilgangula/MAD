package com.example.midterm;

public class UserPOJO {
    String name;
    String email;

    public UserPOJO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    String password;

    public String getName() {
        return name;
    }

    public UserPOJO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
