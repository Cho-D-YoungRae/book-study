package mockitobasic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

class MockTest {

    @Test
    void mockTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(GameLevel.EASY)).willReturn("123");

        String num = genMock.generate(GameLevel.EASY);
        assertThat(num).isEqualTo("123");
    }

    @Test
    void mockThrowTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(null)).willThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> genMock.generate(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void voidMethodWillThrowTest() {
        List<String> mockList = mock(List.class);

        willThrow(UnsupportedOperationException.class)
                .given(mockList).clear();

        assertThatThrownBy(() -> mockList.clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    /**
     * Mockito 는 일치하는 스텁 설정이 ㅇ벗을 경우 리턴 타입의 기본 값을 리턴한다.
     * 예를 들어 리턴 타입이 int 면 0을 리턴하고 boolean 이면 false 를 리턴한다.
     * 기본 데이터 타입이 아닌 String 이나 List 와 같은 참조 타입이면 null 을 리턴한다.
     */
    @Test
    void notGivenArg_ThenReturnNull() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(GameLevel.EASY)).willReturn("123");

        String num = genMock.generate(GameLevel.NORMAL);
        assertThat(num).isNull();
    }

    /**
     * Mockito 클래스와 BDDMockito 클래스는
     * ArgumentMatchers 클래스를 상속하고 있으므로 해당 클래스 내 메서드를 사용해도 된다.
     * ArgumentMatchers 의 메서드
     * - any(): 임의 타입에 대한 일치
     * - anyInt(), anyShort(), anyLong(), anyByte(), anyChar(), anyDouble(), anyFloat(), anyBoolean(): 기본 데이터 타입에 대한 임의 값 일치
     * - anyString(): 문자열에 대한 임의 값 일치
     * - anyList(), anySet(), anyMap(), anyCollection():  임의 콜렉션에 대한 일치
     * - matches(String), matches(Pattern): 정규 표현식을 이용한 String 값 일치 여부
     * - eq(값): 특정 값과 일치 여부
     */
    @Test
    void anyMatchTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(any())).willReturn("456");

        String num1 = genMock.generate(GameLevel.EASY);
        assertThat(num1).isEqualTo("456");

        String num2 = genMock.generate(GameLevel.NORMAL);
        assertThat(num2).isEqualTo("456");
    }

    /**
     * ArgumentMatchers 의 anyInt(), any() 등의 메서드는
     * 내부적으로 인자의 일치 여부를 판단하기 위해 ArgumentMatcher 를 등록한다.
     * Mockito 는 한 인자라도 ArgumentMatcher 를 사용해서 설정한 경우
     * 모든 인자를 ArgumentMatcher 를 이용해서 설정하도록 하고있다.
     */
    @Test
    void mixAnyAndEq() {
        List<String> mockList = mock(List.class);

        // given(mockList.set(anyInt(), "123")).willReturn("456"); // >> 이와 같이 하면 예외 발생
        given(mockList.set(anyInt(), eq("123"))).willReturn("456");

        String old = mockList.set(5, "123");
        assertThat(old).isEqualTo("456");
    }

    /**
     * 메서드 호출 횟수를 검증하기 위해 Mockito 클래스가 제공하는 메서드
     * - only(): 한번만 호출
     * - times(): 지정한 횟수만큼 호출
     * - never(): 호출하지 않음
     * - atLeast(int): 적어도 지정한 횟수만큼 호출
     * - atLeastOnce(1): atLeast(1)과 동일
     * - atMost(int): 최대 지정한 횟수만큼 호출
     */
    @Test
    void init() {
        GameNumGen genMock = mock(GameNumGen.class);
        Game game = new Game(genMock);
        game.init(GameLevel.EASY);

        then(genMock).should(only()).generate(any());
    }
}
