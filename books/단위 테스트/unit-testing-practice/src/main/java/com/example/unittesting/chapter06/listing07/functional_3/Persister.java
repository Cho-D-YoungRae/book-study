package com.example.unittesting.chapter06.listing07.functional_3;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class Persister {

    public List<FileContent> readDirectory(String directoryName) {
        return getFilePaths(directoryName)
                .stream()
                .map(path -> new FileContent(path, read(path)))
                .toList();
    }

    private List<Path> getFilePaths(String directoryName) {
        try (Stream<Path> pathStream = Files.list(Path.of(directoryName))) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<String> read(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void applyUpdate(FileUpdate fileUpdate) {
        write(fileUpdate.path(), fileUpdate.newContent());
    }

    private void write(Path filePath, String newRecord) {
        try {
            Files.writeString(filePath, newRecord, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
