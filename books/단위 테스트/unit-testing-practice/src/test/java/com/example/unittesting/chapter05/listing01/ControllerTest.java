package com.example.unittesting.chapter05.listing01;

import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ControllerTest {

    @Test
    void sending_a_greetings_email() {
        // Mock(도구)로 mock(목) 생성
        IEmailGateway emailGatewayMock = mock(IEmailGateway.class);
        Controller sut = new Controller(emailGatewayMock, null);

        sut.greetUser("user@email.com");

        then(emailGatewayMock).should()
                .sendGreetingEmail("user@email.com");
    }

    @Test
    void creating_a_report() {
        // Mock(도구)로 스텁 생성
        IDatabase databaseStub = mock(IDatabase.class);
        given(databaseStub.getNumberOfUsers()).willReturn(10);
        Controller sut = new Controller(null, databaseStub);

        sut.createReport();

        then(databaseStub).should()
                .getNumberOfUsers();
    }

}