package com.example.unittesting.chapter06.listing04_06;

import java.time.LocalDateTime;

public record Comment(
        String text,
        String author,
        LocalDateTime createdAt
) {
}
