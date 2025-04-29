package com.example.unittesting.chapter05.listing01;

public class Controller {

    private final IEmailGateway emailGateway;
    private final IDatabase database;

    public Controller(IEmailGateway emailGateway, IDatabase database) {
        this.emailGateway = emailGateway;
        this.database = database;
    }

    public void greetUser(String userEmail) {
        emailGateway.sendGreetingEmail(userEmail);
    }

    public Report createReport() {
        int numberOfUsers = database.getNumberOfUsers();
        return new Report(numberOfUsers);
    }
}
