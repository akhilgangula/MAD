package com.example.homework02;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Utils {

    public static final String ALL_STATES = "All States";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<DataServices.User> manipulate(String filterBy, String sortBy, boolean order) {
        List<DataServices.User> filteredUsers = DataServices.getAllUsers();
        if (filterBy != null && !filterBy.equals(Utils.ALL_STATES)) {
            filteredUsers = filteredUsers
                    .stream()
                    .filter(user -> user.state.equals(filterBy))
                    .collect(Collectors.toList());
        }
        if (sortBy != null) {
            switch (sortBy) {
                case "Age":
                    filteredUsers.sort(order ? new SortByAge() : (new SortByAge()).reversed());
                    break;
                case "Name":
                    filteredUsers.sort(order ? new SortByName() : (new SortByName()).reversed());
                    break;
                case "State":
                    filteredUsers.sort(order ? new SortByState() : (new SortByState()).reversed());
                    break;
                default:
                    throw new RuntimeException("Sort function undefined");

            }
        }
        return filteredUsers;
    }

    static class SortByAge implements Comparator<DataServices.User> {

        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return user.age - t1.age;
        }
    }

    static class SortByName implements Comparator<DataServices.User> {

        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return user.name.compareTo(t1.name);
        }
    }

    static class SortByState implements Comparator<DataServices.User> {

        @Override
        public int compare(DataServices.User user, DataServices.User t1) {
            return user.state.compareTo(t1.state);
        }
    }
}