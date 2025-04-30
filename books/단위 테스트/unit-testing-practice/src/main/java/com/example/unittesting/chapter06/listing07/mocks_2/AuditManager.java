package com.example.unittesting.chapter06.listing07.mocks_2;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class AuditManager {

    private static final int START_INDEX = 0;

    private final int maxEntriesPerFile;
    private final String directoryName;
    private final IFileSystem fileSystem;

    public AuditManager(int maxEntriesPerFile, String directoryName, IFileSystem fileSystem) {
        this.maxEntriesPerFile = maxEntriesPerFile;
        this.directoryName = directoryName;
        this.fileSystem = fileSystem;
    }

    public void addRecord(String visitorName, LocalDateTime timeOfVisit) {
        List<Path> filePaths = fileSystem.getFilePaths(directoryName);
        List<Path> sorted = sortByIndex(filePaths);

        String newRecord = visitorName + ";" + timeOfVisit;

        if (sorted.isEmpty()) {
            Path newFilePath = Path.of(directoryName, fileName(START_INDEX));
            fileSystem.write(newFilePath, newRecord);
            return;
        }

        Path currentFilePath = sorted.getLast();
        int currentFileIndex = getIndex(currentFilePath);

        List<String> lines = fileSystem.read(currentFilePath);
        if (lines.size() < maxEntriesPerFile) {
            fileSystem.write(currentFilePath, newRecord);
        } else {
            int newIndex = currentFileIndex + 1;
            String newName = fileName(newIndex);
            Path newFilePath = Path.of(directoryName, newName);
            fileSystem.write(newFilePath, newRecord);
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

}
