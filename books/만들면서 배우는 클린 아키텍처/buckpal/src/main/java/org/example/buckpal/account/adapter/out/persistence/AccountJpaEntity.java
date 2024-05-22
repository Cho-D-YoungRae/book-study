package org.example.buckpal.account.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "account")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class AccountJpaEntity {

    @Id
    @GeneratedValue
    private Long id;
}
