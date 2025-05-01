package com.example.unittesting.chapter07.canexecute;

public class UserFactory {

    public static User create(Object[] data) {
        if (data == null || data.length < 4) {
            throw new IllegalArgumentException();
        }
        int id = (int) data[0];
        String email = (String) data[1];
        UserType type = (UserType) data[2];
        boolean emailConfirmed = (boolean) data[3];

        return new User(id, email, type, emailConfirmed);
    }
}
