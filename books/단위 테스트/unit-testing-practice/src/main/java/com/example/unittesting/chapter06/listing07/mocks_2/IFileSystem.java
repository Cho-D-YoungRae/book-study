package com.example.unittesting.chapter06.listing07.mocks_2;

import java.nio.file.Path;
import java.util.List;

public interface IFileSystem {

    List<Path> getFilePaths(String directoryName);

    void write(Path filePath, String content);

    List<String> read(Path filePath);
}
