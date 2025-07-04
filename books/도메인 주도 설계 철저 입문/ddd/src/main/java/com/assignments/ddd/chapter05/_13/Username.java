package com.assignments.ddd.chapter05._13;

import org.springframework.util.Assert;

public record Username(
        String value
) {

    public Username {
        Assert.notNull(value, "username은 null 일 수 없습니다.");
        Assert.isTrue(value.length() >= 3, "username은 3글자 이상이어야 함");
    }
}
