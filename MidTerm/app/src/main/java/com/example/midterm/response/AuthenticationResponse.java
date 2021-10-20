package com.example.midterm.response;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    public String getToken() {
        return token;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    String token, user_id, user_fullname;
}
