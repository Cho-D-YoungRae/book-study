package com.example.unittesting.chapter9.v2;

import com.example.unittesting.storage.CompanyJpaRepository;
import com.example.unittesting.storage.UserJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    @Test
    void changing_email_from_corporate_to_non_corporate() {
        // Arrange
        var db = new Database(userJpaRepository, companyJpaRepository);
        var company = createCompany("mycorp.com", 1, db);
        var user = createUser("user@mycorp.com", UserType.EMPLOYEE, db);

        var busMock = mock(IBus.class);
        var messageBus = new MessageBus(busMock);
        var loggerMock = mock(IDomainLogger.class);
        var eventDispatcher = new EventDispatcher(messageBus, loggerMock);
        var sut = new UserController(db, eventDispatcher);

        // Act
        sut.changeEmail(user.id(), "new@gmail.com");

        // Assert
        var updatedUser = db.getUserById(user.id());
        assertThat(updatedUser.email()).isEqualTo("new@gmail.com");
        assertThat(updatedUser.type()).isEqualTo(UserType.CUSTOMER);

        var updatedCompany = db.getCompanyByDomainName(company.domainName());
        assertThat(updatedCompany.numberOfEmployees()).isEqualTo(0);

        then(busMock).should().send("Type: USER EMAIL CHANGED; Id: 1; NewEmail: new@gmail.com");
        then(loggerMock).should().userTypeChanged(
                user.id(),
                UserType.EMPLOYEE,
                UserType.CUSTOMER
        );
    }

    @Test
    void changing_email_from_corporate_to_non_corporate_spy() {
        // Arrange
        var db = new Database(userJpaRepository, companyJpaRepository);
        var company = createCompany("mycorp.com", 1, db);
        var user = createUser("user@mycorp.com", UserType.EMPLOYEE, db);

        var busSpy = new BusSpy();
        var messageBus = new MessageBus(busSpy);
        var loggerMock = mock(IDomainLogger.class);
        var eventDispatcher = new EventDispatcher(messageBus, loggerMock);
        var sut = new UserController(db, eventDispatcher);

        // Act
        sut.changeEmail(user.id(), "new@gmail.com");

        // Assert
        var updatedUser = db.getUserById(user.id());
        assertThat(updatedUser.email()).isEqualTo("new@gmail.com");
        assertThat(updatedUser.type()).isEqualTo(UserType.CUSTOMER);

        var updatedCompany = db.getCompanyByDomainName(company.domainName());
        assertThat(updatedCompany.numberOfEmployees()).isEqualTo(0);

        busSpy
                .shouldSendNumberOfMessages(1)
                .withEmailChangedMessage(user.id(), "new@gmail.com");
        then(loggerMock).should().userTypeChanged(
                user.id(),
                UserType.EMPLOYEE,
                UserType.CUSTOMER
        );
    }

    private Company createCompany(String domainName, int numberOfEmployees, Database db) {
        var company = new Company(domainName, numberOfEmployees);
        db.saveCompany(company);
        return company;
    }

    private User createUser(String email, UserType type, Database db) {
        var user = new User(1L, email, type, false);
        db.saveUser(user);
        return user;
    }
}