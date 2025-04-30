package com.example.unittesting.chapter06.listing07.functional_3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuditManagerTest {

    @Test
    void a_new_file_is_created_when_the_current_file_overflows() {
        AuditManager sut = new AuditManager(3);
        List<FileContent> files = List.of(
                new FileContent(Path.of("audit_0.txt"), List.of()),
                new FileContent(Path.of("audit_1.txt"), List.of(
                        "Peter;2019-04-06T16:30:00",
                        "Jane;2019-04-06T16:40:00",
                        "Jack;2019-04-06T17:00:00"
                ))
        );

        FileUpdate fileUpdate = sut.addRecord(
                files, "Alice", LocalDateTime.of(2019, 4, 6, 18, 0));

        assertThat(fileUpdate.path()).isEqualTo(Path.of("audit_2.txt"));
        assertThat(fileUpdate.newContent()).isEqualTo("Alice;2019-04-06T18:00");
        assertThat(fileUpdate).isEqualTo(new FileUpdate(Path.of("audit_2.txt"), "Alice;2019-04-06T18:00"));
    }

}