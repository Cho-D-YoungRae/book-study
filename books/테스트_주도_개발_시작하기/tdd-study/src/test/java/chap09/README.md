# Chapter 9. 테스트 범위와 종류

## 테스트 범위

### 기능 테스트와 E2E 테스트

- 사용자 입장에서 시스템이 제공하는 기능이 올바르게 동작하는지 확인
- 시스템을 구동하고 사용하는데 필요한 모든 요소 필요
- 사용자가 직접 사용하는 웹 브라우저나 모바일 앱부터 시작해서 데이터베이스나 외부 서비스에 이르기까지 모든 구성 요소를 하나로 엮어서 진행
    - 끝(브라우저)에서 끝(데이터베이스)까지 올바른지 검사하기 때문에 E2E(End to End) 테스트로도 볼 수 있음

### 통합 테스트

- 시스템의 각 구성 요소가 올바르게 연동되는지 확인
- 기능 테스트가 사용자 입장에서 테스트하는데 반해 통합 테스트는 소프트웨어의 코드를 직접 테스트
    - 모바일 앱을 예로 들면 기능 테스트는 앱을 통해 기능을 테스트한다면 통합 테스트는 서버의 코드를 직접 테스트

### 단위 테스트

- 개별 코드나 컴포넌트가 기대한대로 동작하는지 확인
- 한 클래스나 한 메서드와 같ㅇ느 작은 범위를 테스트
- 일부 의존 대상은 스텁이나 모의 객체 등을 이용해서 대역으로 대체

## 외부 연동이 필요한 테스트 예

### 스프링 부트와 DB 통합 테스트

- 통합 테스트는 실제 DB 사용

### WireMock 을 이용한 REST 클라이언트 테스트

- `WireMock` 을 사용하면 서버 API 를 스텁으로 대체 가능
- `WireMockServer` 는 HTTP 서버를 흉내 가능
    - 테스트 실행 전에 `WireMockServer` 시작 -> 실제 HTTP 서버가 뜸
    - 테스트에서 `WireMockServer` 의 동작을 기술
    - HTTP 연동을 수행하는 테스트를 실행
    - 테스트 실행 후에 `WireMockServer` 를 중지

```java
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardNumberValidatorTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().port(8089));
        wireMockServer.start();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void valid() {
        wireMockServer.stubFor(post(urlEqualTo("/card"))
                .withRequestBody(equalTo("1234567890"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("ok"))
        );

        CardNumberValidator validator =
                new CardNumberValidator("http://localhost:8089");
        CardValidity validity = validator.validate("1234567890");
        assertEquals(CardValidity.VALID, validity);
    }

    @Test
    void timeout() {
        wireMockServer.stubFor(post(urlEqualTo("/card"))
                .willReturn(aResponse()
                        .withFixedDelay(5000))
        );

        CardNumberValidator validator =
                new CardNumberValidator("http://localhost:8089");
        CardValidity validity = validator.validate("1234567890");
        assertEquals(CardValidity.TIMEOUT, validity);
    }
}
```

### 스프링 부트의 내장 서버를 이용한 API 기능 테스트

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiE2ETest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void weakPwResponse() {
        String reqBody = "{\"id\": \"id\", \"pw\": \"123\", \"email\": \"a@a.com\" }";
        RequestEntity<String> request =
                RequestEntity.post(URI.create("/users"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(reqBody);

        ResponseEntity<String> response = restTemplate.exchange(
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("WeakPasswordException"));
    }
}
```
