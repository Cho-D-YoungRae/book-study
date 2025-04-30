package com.example.unittesting.chapter06.listing07.mocks_2;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class AuditManagerTest {

    @Test
    void a_new_file_is_created_for_the_first_entry() {
        IFileSystem fileSystemMock = mock(IFileSystem.class);
        given(fileSystemMock.getFilePaths("audits"))
                .willReturn(List.of());
        AuditManager sut = new AuditManager(3, "audits", fileSystemMock);

        sut.addRecord("Peter", LocalDateTime.of(2019, 4, 9, 13, 0));

        then(fileSystemMock).should()
                .write(Path.of("audits", "audit_0.txt"), "Peter;2019-04-09T13:00");
    }

    @Test
    void a_new_file_is_created_when_the_current_file_is_overflows() {
        IFileSystem fileSystemMock = mock(IFileSystem.class);
        given(fileSystemMock.getFilePaths("audits"))
                .willReturn(List.of(
                        Path.of("audits", "audit_0.txt"),
                        Path.of("audits", "audit_1.txt")
                ));
        given(fileSystemMock.read(Path.of("audits", "audit_1.txt")))
                .willReturn(List.of(
                        "Peter; 2019-04-06T16:30:00",
                        "Jane; 2019-04-06T16:40:00",
                        "Jack; 2019-04-06T17:00:00"
                ));
        AuditManager sut = new AuditManager(3, "audits", fileSystemMock);

        sut.addRecord("Alice", LocalDateTime.of(2019, 4, 6, 18, 0));

        then(fileSystemMock).should()
                .write(Path.of("audits", "audit_2.txt"), "Alice;2019-04-06T18:00");
    }
}