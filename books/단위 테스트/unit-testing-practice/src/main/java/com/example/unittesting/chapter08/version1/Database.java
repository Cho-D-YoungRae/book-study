package com.example.unittesting.chapter08.version1;

import com.example.unittesting.storage.CompanyEntity;
import com.example.unittesting.storage.CompanyJpaRepository;
import com.example.unittesting.storage.UserEntity;
import com.example.unittesting.storage.UserJpaRepository;

public class Database {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;

    public Database(UserJpaRepository userJpaRepository, CompanyJpaRepository companyJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.companyJpaRepository = companyJpaRepository;
    }

    public User getUserById(long userId) {
        return userJpaRepository.findById(userId)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getEmail(),
                        entity.getType(),
                        entity.isEmailConfirmed()
                )).orElseThrow(() -> new IllegalArgumentException("User not found. Id: " + userId));
    }

    public void saveUser(User user) {
        userJpaRepository.findById(user.id())
                .ifPresentOrElse(
                        entity -> {
                            entity.update(user.email(), user.type(), user.emailConfirmed());
                            userJpaRepository.save(entity);
                        },
                        () -> userJpaRepository.save(new UserEntity(
                                user.id(), user.email(), user.type(), user.emailConfirmed()
                        ))
                );
    }

    public Company getCompanyByDomainName(String domainName) {
        return companyJpaRepository.findByDomainName(domainName)
                .map(entity -> new Company(entity.getDomainName(), entity.getNumberOfEmployees()))
                .orElseThrow(() -> new IllegalArgumentException("Company not found. Domain name: " + domainName));
    }

    public void saveCompany(Company company) {
        companyJpaRepository.findByDomainName(company.domainName())
                .ifPresentOrElse(
                        entity -> {
                            entity.update(company.numberOfEmployees());
                            companyJpaRepository.save(entity);
                        },
                        () -> companyJpaRepository.save(new CompanyEntity(
                                company.domainName(), company.numberOfEmployees()
                        ))
                );
    }
}
