package com.assignments.ddd.chapter05._13;

import java.util.Optional;

public interface IUserRepository {

    void save(User user);

    Optional<User> find(Username username);
}
