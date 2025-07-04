package com.assignments.ddd.chapter05._13;

import org.springframework.util.Assert;

public class User {

    private final UserId id;
    private final Username username;

    public User(UserId id, Username username) {
        Assert.notNull(id, "id는 null일 수 없습니다.");
        Assert.notNull(username, "username는 null일 수 없습니다.");
        this.id = id;
        this.username = username;
    }

    public UserId getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public record UserId(long value) {
    }

}