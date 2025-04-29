package com.example.unittesting.chapter05.listing06;

public class UserController {

    public void renameUser(int userId, String newName) {
        User user = getUserFromDatabase(userId);
        user.setName(newName);
        saveUserToDatabase(user);
    }

    private void saveUserToDatabase(User user) {
    }

    private User getUserFromDatabase(int userId) {
        return new User();
    }
}
