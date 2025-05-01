package com.example.unittesting.chapter07.refactored_3;

public class UserFactory {

    public static User create(Object[] data) {
        if (data == null || data.length < 3) {
            throw new IllegalArgumentException();
        }
        int id = (int) data[0];
        String email = (String) data[1];
        UserType type = (UserType) data[2];

        return new User(id, email, type);
    }
}
