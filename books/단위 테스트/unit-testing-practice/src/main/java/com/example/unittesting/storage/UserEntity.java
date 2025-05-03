package com.example.unittesting.storage;

import com.example.unittesting.chapter08.version1.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uk_users__email", columnNames = "email")
)
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "email_confirmed", nullable = false)
    private boolean emailConfirmed;

    protected UserEntity() {
    }

    public UserEntity(Long id, String email, String type, boolean emailConfirmed) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.emailConfirmed = emailConfirmed;
    }

    public void update(String email, String type, boolean emailConfirmed) {
        this.email = email;
        this.type = type;
        this.emailConfirmed = emailConfirmed;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }
}
