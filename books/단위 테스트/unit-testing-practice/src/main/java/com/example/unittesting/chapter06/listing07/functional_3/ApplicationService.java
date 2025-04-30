package com.example.unittesting.chapter06.listing07.functional_3;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ApplicationService {

    private final String directoryName;
    private final AuditManager auditManager;
    private final Persister persister;

    public ApplicationService(
            String directoryName,
            AuditManager auditManager,
            Persister persister
    ) {
        this.directoryName = directoryName;
        this.auditManager = auditManager;
        this.persister = persister;
    }

    public void addRecord(String visitorName, LocalDateTime timeOfVisit) {
        List<FileContent> files = persister.readDirectory(directoryName);
        FileUpdate fileUpdate = auditManager.addRecord(files, visitorName, timeOfVisit);
        persister.applyUpdate(fileUpdate);
    }
}
