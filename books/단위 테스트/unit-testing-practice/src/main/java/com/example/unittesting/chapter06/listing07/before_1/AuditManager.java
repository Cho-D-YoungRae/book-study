package com.example.unittesting.chapter06.listing07.before_1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AuditManager {

    private static final int START_INDEX = 0;

    private final int maxEntriesPerFile;
    private final String directoryName;

    public AuditManager(int maxEntriesPerFile, String directoryName) {
        this.maxEntriesPerFile = maxEntriesPerFile;
        this.directoryName = directoryName;
    }

    public void addRecord(String visitorName, LocalDateTime timeOfVisit) {
        List<Path> filePaths = getFilePaths();
        List<Path> sorted = sortByIndex(filePaths);

        String newRecord = visitorName + ";" + timeOfVisit;

        if (sorted.isEmpty()) {
            Path newFilePath = Path.of(directoryName, fileName(START_INDEX));
            write(newFilePath, newRecord);
            return;
        }

        Path currentFilePath = sorted.getLast();
        int currentFileIndex = getIndex(currentFilePath);

        List<String> lines = read(currentFilePath);
        if (lines.size() < maxEntriesPerFile) {
            write(currentFilePath, newRecord);
        } else {
            int newIndex = currentFileIndex + 1;
            String newName = fileName(newIndex);
            Path newFilePath = Path.of(directoryName, newName);
            write(newFilePath, newRecord);
        }
    }

    private List<Path> getFilePaths() {
        try (Stream<Path> pathStream = Files.list(Path.of(directoryName))) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<Path> sortByIndex(List<Path> filePaths) {
        return filePaths.stream()
                .sorted(Comparator.comparingDouble(this::getIndex))
                .toList();
    }

    private int getIndex(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int extIndex = fileName.lastIndexOf('.');
        String fileNameWithoutExt = extIndex == -1 ? fileName : fileName.substring(0, extIndex);
        return Integer.parseInt(fileNameWithoutExt.split("_")[1]);
    }

    private String fileName(int index) {
        return String.format("audit_%d.txt", index);
    }

    private void write(Path filePath, String newRecord) {
        try {
            Files.writeString(filePath, newRecord, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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
}
