package com.example.unittesting.chapter07.refactored_1;

public class UserController {

    private final Database database = new Database();
    private final MessageBus messageBus = new MessageBus();

    public void changeEmail(int userId, String newEmail) {
        Object[] data = database.getUserById(userId);
        String email = (String) data[0];
        UserType type = (UserType) data[1];
        User user = new User(userId, email, type);

        Object[] companyData = database.getCompany();
        String companyDomainName = (String) companyData[0];
        int numberOfEmployees = (int) companyData[1];

        int newNumberOfEmployees = user.changeEmail(newEmail, companyDomainName, numberOfEmployees);

        database.saveCompany(newNumberOfEmployees);
        database.saveUser(user);
        messageBus.sendEmailChangedMessage(userId, newEmail);
    }
}
