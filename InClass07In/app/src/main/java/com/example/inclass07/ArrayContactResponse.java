package com.example.inclass07;

import java.util.ArrayList;

public class ArrayContactResponse {
    public ArrayContactResponse(ArrayList<Contact> contacts, String status) {
        this.contacts = contacts;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ArrayContactResponse{" +
                "contacts=" + contacts +
                '}';
    }

    ArrayList<Contact> contacts;
    String status;
}
