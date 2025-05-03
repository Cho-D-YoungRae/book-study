package com.example.unittesting.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {

    Optional<CompanyEntity> findByDomainName(String domainName);
}
