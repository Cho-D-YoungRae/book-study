# Chapter 10. 테스트 코드와 유지보수

## 테스트 코드와 유지 보수

- 테스트 코드는 그 자체로 코드이기 때문에 제품 코드와 동일하게 유지보수 대상이 됨
- 테스트 코드를 유지보수하는데 시간이 많이 들기 시작하면 점점 테스트 코드를 손보지 않게 됨

## 변수나 필드를 사용해서 기댓값 표현하지 않기

- 실수할 여지가 많아짐
- 코드를 보기 어려워짐

```java
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DateTest {

    // BAD
    @Test
    void dateFormat_BAD() {
        LocalDate date = LocalDate.of(1945, 8, 15);
        String dateStr = formatDate(date);
        assertThat(dateStr).isEqualTo(date.getYear() + "년 " + date.getMonthValue() + "월 " + date.getDayOfMonth() + "일");
    }

    // GOOD
    @Test
    void dateFormat_GOOD() {
        LocalDate date = LocalDate.of(1945, 8, 15);
        String dateStr = formatDate(date);
        assertThat(dateStr).isEqualTo("1945년 8월 15일");
    }
}
```

## 두 개 이상을 검증하지 않기

- 한 테스트에서 검증하는 내용이 두 개 이상이면 테스트 결과를 확인할 때 집중도가 떨어짐
- 첫 번째 검증 대상을 통과시켜야 비로소 두 번째 검증이 성공했는지 여부를 확인할 수 있음
  - 첫 번째 검증에 실패하면 테스트는 거기서 멈춤
- 테스트 메서드가 반드시 한 가지만 검증해야 하는 것은 아니지만, 검증 대상 명확하게 구분된다면 테스트 메서드도 구분하는 것이 유지보수에 유리

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserRegisterMockTest {

    // BAD ================================================================
    @DisplayName("같은 ID가 없으면 가입에 성공하고 메일을 전송")
    @Test
    void registerAndSendMail() {
        userRegister.register("id", "pw", "email@email.com");
        
        // 검증1: 회원 데이터가 올바르게 저장되었는지 검증
        User savedUser = fakeRepository.findById("id");
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getEmail()).isEqualTo("email@email.com");

        // 검증2: 이메일 발송을 요청했는지 검증
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockEmailNotifier).should().sendRegisterEmail(captor.capture());
        String realEmail = captor.getValue();
        assertThat(realEmail).isEqualTo("email@email.com");
    }
    
    // GOOD ================================================================
    @DisplayName("같은 ID가 없으면 가입 성공함")
    @Test
    void noDupId_RegisterSuccess() {
        userRegister.register("id", "pw", "email@email.com");

        User savedUser = fakeRepository.findById("id");
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getEmail()).isEqualTo("email@email.com");
    }
    
    @DisplayName("가입하면 메일을 전송함")
    @Test
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pw", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockEmailNotifier).should().sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        assertThat(realEmail).isEqualTo("email@email.com");
    }
}
```

## 정확하게 일치하는 값으로 모의 객체 설정하지 않기

- 모듸 객체는 가능한 범용적인 값을 사용해서 기술
- 한정된 값에 일치하도록 모의 객체를 사용하면 약간의 코드 수정만으로도 테스트는 실패
  - 테스트 코드의 일부 값을 수정하면 모의 객체 관련 코드도 함께 수정하는 번거로움

```java
class UserRegisterMockTest {

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPassword() {
        // BAD
        // given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);
        // GOOD
        given(mockPasswordChecker.checkPasswordWeak(anyString())).willReturn(true);

        assertThatThrownBy(() -> userRegister.register("id", "pw", "email"))
                .isInstanceOf(WeakPasswordException.class);
    }
}
```

## 과도하게 구현 검증하지 않기

- 모의 객체를 이용해서 내부 구현을 검증하는 코드를 많이 작성하는 것은 테스트 코드 유지보수에 도움이 되지 않음
- 내부 구현을 검증하는 것이 나쁜 것은 아니지만 구현을 조금만 변경해도 테스트가 깨질 가능성이 커짐
- 내부 구현은 언제든지 바뀔 수 있기 떄문에 테스트 코드는 내부 구현보다 실행 결과를 검증

## 셋업을 이용해서 중복된 상황을 설정하지 않기

```java
@BeforeEach
void setUp() {
    데이터 저장 등 테스트 마다 중복되는 테스트 상황 구성 세팅
}
```

- 위와 같이 중복을 제거하면 테스트에서는 실패 했을 때 코드를 위아래로 이동하면서 실패한 원인을 분석해야 될 수 있음
- 테스트가 깨지기 쉬운 구조가 됨
  - 모든 테스트 메서드가 동일한 상황 코드를 공유하기 떄문에 조금만 내용을 변경해도 테스트가 깨질 수 있음
- 각 테스트 메서드는 별도 프로그램으로서 검증 내용을 스스로 잘 설명할 수 있어야 하고 그러기 위해 상황 구성 코드가 테스트 메서드 안에 위치해야 함

### 통합 테스트에서 데이터 공유 주의하기

```java
@SpringBootTest
@Sql("classpath:init-data.sal")
class UserRegisterIntTestUsingSql {
    ...
}
```

- DB 연동을 포함한 통합 테스트를 실행하려면 DB 데이터를 알맞게 구성해야 함
  - 스프링에서는 위와 같이 `@Sql` 을 이용할 수 있음
  - 셋업 메서드를 이용한 상황 설정과 마찬가지로 초기화를 위한 쿼리 파일을 조금만 변경해도 많은 테스트가 깨질 수 있고 테스트가 깨지면 관련된 쿼리 파일을 같이 봐야 함
- 통합 테스트 코드를 만들 때는 다음의 두 가지로 초기화 데이터를 나눠서 생각
  - 모든 테스트가 같은 값을 사용하는 데이터
    - ex) 코드값 데이터
    - 모든 테스트가 동일한 값을 사용해야 되는 값
  - 테스트 메서드에서만 필요한 데이터
    - ex) 중복 ID 검사를 위한 회원 데이터
    - 특정 테스트 메서드에서만 의미 있는 데이터이므로 모든 테스트가 공유할 필요 X
    - 특정 테스트에서만 생성해서 테스트 코드가 완전한 하나가 되도록 해야 함

### 통합 테스트의 상황 설정을 위한 보조 클래스 이용

#### 문제

```java
import chap07.DupIdException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql("classpath:init-data.sal")
class UserRegisterIntTestUsingSql {

    @Autowired
    private UserRegister userRegister;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void dupId() {
        jdbcTemplate.update(
                "insert into user values (?, ?, ?) " +
                "on duplicate key update password = ?, email = ?",
                "cbk", "pw", "cbk@cbk.com", "pw", "cbk@cbk.com");
        assertThatThrownBy(() -> userRegister.register("cbk", "strongpw", "email@email.com"))
                .isInstanceOf(DupIdException.class);
    }
}
```

- 직접 쿼리를 실행 중
- 이와 유사한 쿼리는 테스트에서 많이 사용될 것
  - 상황을 만들기 위한 코드가 여러 테스트에서 중복
  - 테이블 이름이나 칼럼 이름이 바뀌면 여러 테스트 메서드를 수정해야 하므로 유지보수에 좋지 않음
- 상황 설정을 위한 보조 클래스를 사용해서 이를 해결할 수 있음

#### 해결

```java
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserGivenHelper {

    private final JdbcTemplate jdbcTemplate;
    
    public void givenUser(String id, String pw, String email) {
        jdbcTemplate.update(
                "insert into user values (?, ?, ?) " +
                "on duplicate key update password = ?, email = ?",
                id, pw, email, pw, email);
    }
}
```

```java
import chap07.DupIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql("classpath:init-data.sal")
class UserRegisterIntTestUsingSql {

    @Autowired
    private UserRegister userRegister;

    @AutoWired
    private JdbcTemplate jdbcTemplate;

    private UserGivenHelper userGivenHelper;

    @BeforeEach
    void setUp() {
        userGivenHelper = new UserGivenHelper(jdbcTemplate);
    }

    @Test
    void dupId() {
        userGivenHelper.givenUser("cbk", "pw", "cbk@cbk.com");
        assertThatThrownBy(() -> userRegister.register("cbk", "strongpw", "email@email.com"))
                .isInstanceOf(DupIdException.class);
    }
}
```

## 실행 환경이 다르다고 실패하지 않기

- 같은 테스트 메서드가 로컬 개발 환경, 빌드 서버, 윈도우, 맥 OS 등 실행 환경에 따라 성공하거나 실패하면 안됨
- 실행 환경에 따라 문제가 되는 예
  - 파일 경로
    - 프로젝트 폴더를 기준으로 상대 경로 사용
  - 파일 생성
    - 임시 폴더 사용
  - 간혹 특정 OS 환경에서만 실행해야 되는 테스트의 경우 JUnit5 가 제공하는 `@EnabledOnOs`, `@DisabledOnOs` 사용

## 실행 시점이 다르다고 실패하지 않기

### 문제

```java
import java.time.LocalDateTime;

public class Member {
    
    private LocalDateTime expiryDate;
    
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
```

- `Member.isExpired()` 를 외부에서 제어 불가능
- 테스트 시에 해당 메서드를 제어할 수 없으므로 특정 시점이 지나면 테스트가 깨질 수 있음
- 테스트 코드에서 시간을 명시적으로 제어할 수 있는 방법을 선택하는 것이 좋음

### 해결1

```java
import java.time.LocalDateTime;

public class Member {
    
    private LocalDateTime expiryDate;
    
    public boolean isExpired(LocalDateTime time) {
        return expiryDate.isBefore(time);
    }
}
```

- 해당 메서드를 명시적으로 제어할 수 있게 됨으로써 원하는 조건에 따른 테스트 코드를 작성 가능

### 해결2

시점을 제어하는 또 다른 방법은 별도의 시간 클래스를 작성하는 것

```java
import java.time.LocalDateTime;

public class BizClock {

    private static BizClock DEFAULT = new BizClock();

    private static BizClock instance = DEFAULT;

    public static void reset() {
        instance = DEFAULT;
    }

    public static LocalDateTime now() {
        return instance.timeNow();
    }

    protected void setInstance(BizClock bizClock) {
        BizClock.instance = bizClock;
    }
    
    public LocalDateTime timeNow() {
        return LocalDateTime.now();
    }
}
```

- instance 필드를 교체할 수 있으므로 BizClock 을 상속받은 하위 클래스를 이용하면 BizClock#now() 가 원하는 시간을 제공하도록 만들 수 있음

```java
public class Member {
    
    private LocalDateTime expiryDate;
    
    public boolean isExpired(LocalDateTime time) {
        return expiryDate.isBefore(BizClock.now());
    }
}
```

### 랜덤하게 실패하지 않기

#### 문제

```java
import java.util.Random;

public class Game {

    private int[] nums;

    public Game() {
        Random random = new Random();
        int firstNo = random.nextInt(10);
        int secondNo = random.nextInt(10);
        int thirdNo = random.nextInt(10);
        this.nums = new int[]{firstNo, secondNo, thirdNo};
    }
    
    // ...
}
```

- 정답이 랜덤하게 만들어져서 원하는 테스트 코드를 작성할 수 없음

#### 해결 1

```java
import java.util.Random;

public class Game {

    private int[] nums;

    public Game(int[] nums) {
        this.nums = nums;
    }
    
    // ...
}
```

- 생성자를 통해 값을 받을 수 있음

#### 해결 2

```java
public class Game {

    private int[] nums;

    public Game(GameNumGen gen) {
        nums = gen.generate();
    }

    // ...
}
```

- 랜덤 값 생성을 다른 객체에 위임

## 필요하지 않은 값은 설정하지 않기

- 검증에 필요한 값만 설정
- 필요하지 않은 값을 설정하느라 고민해야 될 수 있음
- 테스트 코드가 짧아져서 가독성 향상

### 단위 테스트를 위한 객체 생성 보조 클래스

- 상황 구성을 위해 필요한 데이터가 다소 복잡할 수 있음
- null 이면 안 되는 필수 속성이 많다면 상황 구성 코드는 더 복잡해질 수 있음
- 기본 값을 설정할 수 있는 정적 팩토리 메서드 혹은 빌더 패턴을 이용해서 해결 가능

## 조건부로 검증하지 않기

### 문제

```java
import org.junit.jupiter.api.Test;

@Test
class BadConditionalTest {
    void firstShouldBeAdminItem() {
        givenAdminItem(...);
        givenUserItem(...);
        
        List<Item> items = itemService.getItems();

        if (items.size() > 0) {
            assertThat(items.get(0).getType()).isEqualTo(ItemType.ADMIN);
            assertThat(items.get(1).getType()).isEqualTo(ItemType.USER);
        }
    }
}
```

- 테스트는 성공하거나 실패해야 하고 그러려면 반드시 단언을 실행해야 하는데 조건에 따라서 단언을 실행하지 않으면 그 테스트는 성공하지도 실패하지도 않은 테스트가 됨

### 해결

```java
import org.junit.jupiter.api.Test;

@Test
class BadConditionalTest {
    void firstShouldBeAdminItem() {
        givenAdminItem(...);
        givenUserItem(...);
        
        List<Item> items = itemService.getItems();

        if (items.size() > 0) {
            assertThat(items.get(0).getType()).isEqualTo(ItemType.ADMIN);
            assertThat(items.get(1).getType()).isEqualTo(ItemType.USER);
        }
    }
}
```

- 조건에 대한 단언도 추가

## 통합 테스트는 필요하지 않은 범위까지 연동하지 않기

- 테스트하는 대상이 DB와 연동을 처리하는 DAO 인데 @SpringBootTest 를 사용하면 서비스, 컨트롤러 등 모든 스프링 빈이 초기화 됨
  - @JdbcTest 를 이용해서 DataSource, JdbcTemplate 등 DB 연동과 관련된 설정만 초기화

## 더 이상 쓸모 없는 테스트 코드

- 연습용으로 사용한 테스트 코드는 삭제
- 테스트 커버리지를 높이기 위해 게터 등의 단순한 메서드까지 테스트하는 것은 유지보수에 도움 X