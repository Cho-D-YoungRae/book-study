package chap07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRegisterTest {

    private UserRegister userRegister;

    private StubWeakPasswordChecker stubPasswordChecker;

    private MemoryUserRepository fakeRepository;

    private SpyEmailNotifier spyEmailNotifier;

    @BeforeEach
    void setUp() {
        stubPasswordChecker = new StubWeakPasswordChecker();
        fakeRepository = new MemoryUserRepository();
        spyEmailNotifier = new SpyEmailNotifier();
        userRegister = new UserRegister(stubPasswordChecker, fakeRepository, spyEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPassword() {
        stubPasswordChecker.setWeak(true);

        assertThatThrownBy(() -> userRegister.register("id", "pw", "email"))
                .isInstanceOf(WeakPasswordException.class);
    }

    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    @Test
    void dupIdExists() {
        // 이미 같은 ID 존재하는 상황 만들기
        fakeRepository.save(new User("id", "pw1", "email@email.com"));

        assertThatThrownBy(() -> userRegister.register("id", "pw1", "email@email.com"))
                .isInstanceOf(DupIdException.class);
    }

    @DisplayName("같은 ID가 없으면 가입 성공함")
    @Test
    void noDupId_RegisterSuccess() {
        userRegister.register("id", "pw", "email");

        User savedUser = fakeRepository.findById("id");
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getEmail()).isEqualTo("email");
    }

    @DisplayName("가입하면 메일을 전송함")
    @Test
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pw", "email@email.com");

        assertThat(spyEmailNotifier.isCalled()).isTrue();
        assertThat(spyEmailNotifier.getEmail()).isEqualTo("email@email.com");
    }
}
