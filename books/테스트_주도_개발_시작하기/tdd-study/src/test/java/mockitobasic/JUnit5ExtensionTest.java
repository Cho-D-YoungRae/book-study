package mockitobasic;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Mockito 의 JUnit 5의 확장 기능을 사용하면 어노테이션을 이용해서 모의 객체를 생성할 수 있다.
 * 확장 기능을 사용하려면 mockito-junit-jupiter 의존을 추가해야 한다.
 * 기본은 mockito-core
 */
@ExtendWith(MockitoExtension.class)
class JUnit5ExtensionTest {

    @Mock
    private GameNumGen genMock;
}
