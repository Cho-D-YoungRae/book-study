package com.example.distlock;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record LockOwner(@Nullable String owner, @Nullable LocalDateTime expiry) {

    public boolean isOwnedBy(String owner) {
        return this.owner.equals(owner);
    }

    public boolean isExpired() {
        return expiry.isBefore(LocalDateTime.now());
    }
}
