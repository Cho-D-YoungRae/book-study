package com.assignments.ddd.chapter02._21;

import org.springframework.util.StringUtils;

record FullName(
        FirstName firstName,
        LastName lastName
) {

}

record FirstName(String value) {

    FirstName {
        if (StringUtils.hasText(value)) {
            throw new IllegalArgumentException("최소 1글자 이상이어야 함. value=" + value);
        }
    }
}

record LastName(
        String value
) {

    LastName {
        if (StringUtils.hasText(value)) {
            throw new IllegalArgumentException("최소 1글자 이상이어야 함. value=" + value);
        }
    }
}

public class Example21 {
}
