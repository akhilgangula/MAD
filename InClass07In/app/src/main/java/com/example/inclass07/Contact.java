package com.example.inclass07;

import java.io.Serializable;

public class Contact implements Serializable {
    public void setCid(String cid) {
        Cid = cid;
    }

    private String Cid;
    private String Name;

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPhoneType(String phoneType) {
        PhoneType = phoneType;
    }

    private String Email;
    private String Phone;

    public String getCid() {
        return Cid;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPhoneType() {
        return PhoneType;
    }

    private String PhoneType;

    public Contact(String cid, String name, String email, String phone, String phoneType) {
        Cid = cid;
        Name = name;
        Email = email;
        Phone = phone;
        PhoneType = phoneType;
    }

    public Contact() {
    }

    @Override
    public String toString() {
        return "Contact{" +
                "Cid='" + Cid + '\'' +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", PhoneType='" + PhoneType + '\'' +
                '}';
    }
}
