package me.study.testpractice.iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    private Profile profile;

    private Question question;

    private Criteria criteria;

    @BeforeEach
    void create() {
        profile = new Profile("Bull Hockey, Inc");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }

    @Test
    void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        profile.add(new Answer(question, Bool.TRUE));

        criteria.add(new Criterion(new Answer(question, Bool.FALSE), Weight.MUST_MATCH));

        boolean matches = profile.matches(criteria);

        assertThat(matches).isFalse();
    }

    @Test
    void matchAnswersTrueForAnyDontCareCriteria() {
        profile.add(new Answer(question, Bool.TRUE));

        criteria.add(new Criterion(new Answer(question, Bool.FALSE), Weight.DONT_CARE));

        boolean matches = profile.matches(criteria);

        assertThat(matches).isTrue();
    }
}