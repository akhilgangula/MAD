package com.example.register;

import java.io.Serializable;

public class UserDetails implements Serializable {
    private String name;
    private String email;
    private String id;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    private String dept;

    public UserDetails() {
    }

    public UserDetails(String name, String email, String id, String dept) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
