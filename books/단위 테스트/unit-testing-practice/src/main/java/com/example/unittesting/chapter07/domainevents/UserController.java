package com.example.unittesting.chapter07.domainevents;

public class UserController {

    private final Database database = new Database();
    private final MessageBus messageBus = new MessageBus();

    public void changeEmail(int userId, String newEmail) {
        Object[] userData = database.getUserById(userId);
        User user = UserFactory.create(userData);
        if (user.canChangeEmail()) {
            throw new IllegalStateException();
        }

        Object[] companyData = database.getCompany();
        Company company = CompanyFactory.create(companyData);

        user.changeEmail(newEmail, company);

        database.saveCompany(company);
        database.saveUser(user);
        for (EmailChangedEvent event : user.getEmailChangedEvents()) {
            messageBus.sendEmailChangedMessage(event.userId(), event.newEmail());
        }
    }
}
