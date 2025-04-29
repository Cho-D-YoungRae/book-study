package com.example.unittesting.chapter05.listing05;

import java.util.Optional;

/**
 * User 클래스의 API가 잘 설계되지 않은 이유
 * - 속성과 메서드가 둘 다 공개
 * - normalizeName 은 공개 API 로 유출되는 구현 세부사항
 */
public class User {

    public String name;

    public String normalizeName(String name) {
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
        this.name = name;
    }
}
