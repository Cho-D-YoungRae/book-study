package com.example.unittesting.chapter05.listing05;

public class UserController {

    public void renameUser(int userId, String newName) {
        User user = getUserFromDatabase(userId);

        String normalizeName = user.normalizeName(newName);
        user.setName(normalizeName);

        saveUserToDatabase(user);
    }

    private void saveUserToDatabase(User user) {
    }

    private User getUserFromDatabase(int userId) {
        return new User();
    }
}
