package com.example.unittesting.chapter05.listing06;

import java.util.Optional;

public class User {

    private String name;

    private String normalizeName(String name) {
        String result = Optional.ofNullable(name)
                .filter(String::isBlank)
                .map(String::trim)
                .orElse("");

        if (result.length() > 50) {
            return result.substring(0, 50);
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = normalizeName(name);
    }
}
