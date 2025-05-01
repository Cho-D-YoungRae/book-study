package com.example.unittesting.chapter07.refactored_2;

public class UserController {

    private final Database database = new Database();
    private final MessageBus messageBus = new MessageBus();

    public void changeEmail(int userId, String newEmail) {
        Object[] userData = database.getUserById(userId);
        User user = UserFactory.create(userData);

        Object[] companyData = database.getCompany();
        String companyDomainName = (String) companyData[0];
        int numberOfEmployees = (int) companyData[1];

        int newNumberOfEmployees = user.changeEmail(newEmail, companyDomainName, numberOfEmployees);

        database.saveCompany(newNumberOfEmployees);
        database.saveUser(user);
        messageBus.sendEmailChangedMessage(userId, newEmail);
    }
}
