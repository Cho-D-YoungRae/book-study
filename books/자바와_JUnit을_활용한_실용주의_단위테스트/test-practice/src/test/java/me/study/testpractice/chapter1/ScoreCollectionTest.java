package me.study.testpractice.chapter1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreCollectionTest {

    @Test
    void test() {
//        Assertions.fail("Not yet implemented");
    }

    @Test
    void answerArithmeticMeanOfTwoNumbers() {
        ScoreCollection scoreCollection = new ScoreCollection();
        scoreCollection.add(() -> 5);
        scoreCollection.add(() -> 7);

        int actualResult = scoreCollection.arithmeticMean();
        assertThat(actualResult).isEqualTo(6);
    }
}