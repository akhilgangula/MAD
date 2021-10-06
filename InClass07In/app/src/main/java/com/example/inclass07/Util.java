package com.example.inclass07;

import java.util.Arrays;
import java.util.List;

public class Util {
    public static final String CELL = "CELL";
    public static final String OFFICE = "OFFICE";
    public static final String HOME = "HOME";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String TYPE = "type";

    public static final String GET_URL = "https://www.theappsdr.com/contacts/json";
    public static final String URL = "https://www.theappsdr.com/contact/json";
    public static final String URL_CREATE = "/create";
    public static final String URL_UPDATE = "/update";
    public static final String URL_DELETE = "/delete";

    public static final String FRAGMENT_TAG = "fragment_tag";

    public static final String CONTACT_TITLE = "Contacts";
    public static final String NEW_CONTACT_TITLE = "New Contacts";
    public static final String UPDATE_CONTACT_TITLE = "Update Contacts";
    public static final String DETAIL_CONTACT_TITLE = "Detail Contacts";

    public static boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email != null && !email.isEmpty() && email.matches(regex) ;
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^\\(?([0-9]{3})\\)?[-.●]?([0-9]{3})[-.●]?([0-9]{4})$";
        return phone != null && !phone.isEmpty() && phone.matches(regex);
    }

    public static boolean isValidPhoneType(String type) {
        List<String> validTypes = Arrays.asList(CELL, OFFICE, HOME);
        String allCapsType = type.toUpperCase();
        return type!=null && !type.isEmpty() && validTypes.contains(allCapsType);
    }
}
