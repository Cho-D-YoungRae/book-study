package chap01;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpringTest {

    @Test
    void substring() {
        String str = "abcde";
        assertThat(str.substring(2, 4)).isEqualTo("cd");
    }
}
