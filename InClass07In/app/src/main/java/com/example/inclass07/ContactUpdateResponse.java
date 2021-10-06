package com.example.inclass07;

public class ContactUpdateResponse {
    String status;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    Contact contact;
}
