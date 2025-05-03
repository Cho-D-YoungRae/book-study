package com.example.unittesting.chapter08.version1;

public class UserController {

    private static final String COMPANY_DOMAIN_NAME = "mycorp.com";

    private final Database database;
    private final IMessageBus messageBus;

    public UserController(Database database, IMessageBus messageBus) {
        this.database = database;
        this.messageBus = messageBus;
    }

    public void changeEmail(long userId, String newEmail) {
        User user = database.getUserById(userId);

        if (!user.canChangeEmail()) {
            throw new IllegalStateException("User cannot change email");
        }

        Company company = database.getCompanyByDomainName(COMPANY_DOMAIN_NAME);

        UserEmailChangedResult result = user.changeEmail(newEmail, company);
        database.saveCompany(result.company());
        database.saveUser(result.user());
        for (EmailChangedEvent event : result.user().emailChangedEvents()) {
            messageBus.sendEmailChangedMessage(event.userId(), event.newEmail());
        }
    }
}
