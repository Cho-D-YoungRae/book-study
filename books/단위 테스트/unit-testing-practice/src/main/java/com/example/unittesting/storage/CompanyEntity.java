package com.example.unittesting.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "company",
        uniqueConstraints = @UniqueConstraint(name = "uk_company__1", columnNames = "domain_name")
)
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "number_of_employees")
    private int numberOfEmployees;

    protected CompanyEntity() {
    }

    public CompanyEntity(String domainName, int numberOfEmployees) {
        this.domainName = domainName;
        this.numberOfEmployees = numberOfEmployees;
    }

    public void update(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Long getId() {
        return id;
    }

    public String getDomainName() {
        return domainName;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }
}
