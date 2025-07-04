package com.assignments.ddd.chapter05._13;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements IUserRepository {

    private final Map<User.UserId, User> store = new ConcurrentHashMap<>();

    @Override
    public Optional<User> find(Username username) {
        return store.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .map(this::clone)
                .findFirst();
    }

    @Override
    public void save(User user) {
        store.put(user.getId(), clone(user));
    }

    private User clone(User user) {
        return new User(user.getId(), user.getUsername());
    }
}
