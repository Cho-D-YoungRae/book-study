package com.example.unittesting.chapter06.listing07.functional_3;

import java.nio.file.Path;
import java.util.List;

public record FileContent(
        Path path,
        List<String> lines
) {
}
