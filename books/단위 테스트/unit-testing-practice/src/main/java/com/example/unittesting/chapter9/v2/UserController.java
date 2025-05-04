package com.example.unittesting.chapter9.v2;

public class UserController {

    private static final String COMPANY_DOMAIN_NAME = "mycorp.com";

    private final Database database;
    private final EventDispatcher eventDispatcher;

    public UserController(Database database, EventDispatcher eventDispatcher) {
        this.database = database;
        this.eventDispatcher = eventDispatcher;
    }

    public void changeEmail(long userId, String newEmail) {
        User user = database.getUserById(userId);

        if (!user.canChangeEmail()) {
            throw new IllegalStateException("User cannot change email");
        }

        Company company = database.getCompanyByDomainName(COMPANY_DOMAIN_NAME);

        UserEmailChangeResult result = user.changeEmail(newEmail, company);
        database.saveUser(result.user());
        database.saveCompany(result.company());
        eventDispatcher.dispatch(result.user().domainEvents());
    }
}
