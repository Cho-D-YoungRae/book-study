package com.example.unittesting.chapter06.listing07.functional_3;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class AuditManager {

    private static final int START_INDEX = 0;

    private final int maxEntriesPerFile;

    public AuditManager(int maxEntriesPerFile) {
        this.maxEntriesPerFile = maxEntriesPerFile;
    }

    public FileUpdate addRecord(
            List<FileContent> files, String visitorName, LocalDateTime timeOfVisit
    ) {
        List<FileContent> sorted = sortByIndex(files);

        String newRecord = visitorName + ";" + timeOfVisit;

        if (sorted.isEmpty()) {
            return new FileUpdate(Path.of(fileName(START_INDEX)), newRecord);
        }

        FileContent currentFile = sorted.getLast();
        List<String> lines = currentFile.lines();

        if (lines.size() < maxEntriesPerFile) {
            return new FileUpdate(currentFile.path(), newRecord);
        } else {
            int newIndex = getIndex(currentFile.path()) + 1;
            return new FileUpdate(Path.of(fileName(newIndex)), newRecord);
        }
    }

    private List<FileContent> sortByIndex(List<FileContent> files) {
        return files.stream()
                .sorted(Comparator.comparingDouble(f -> getIndex(f.path())))
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
