package com.example.unittesting.chapter06.listing07.functional_3;

import java.nio.file.Path;

public record FileUpdate(
        Path path,
        String newContent
) {
}
