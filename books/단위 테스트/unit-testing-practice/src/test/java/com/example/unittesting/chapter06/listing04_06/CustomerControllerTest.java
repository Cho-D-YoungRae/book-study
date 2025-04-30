package com.example.unittesting.chapter06.listing04_06;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerControllerTest {

    /**
     * 검증부가 많음. 상태 기반 테스트는 종종 훨씬 많은 데이터를 확인해야 하므로 크기가 대폭 커질 수 있음
     * 헬퍼 메서드로 문제를 완화할 수 있지만, 이러한 메서드를 작성하고 유지하는 데 상당한 노력 필요
     */
    @Test
    void adding_a_comment_to_an_article() {
        Article sut = new Article();
        String text = "Comment text";
        String author = "John Doe";
        LocalDateTime now = LocalDateTime.of(2019, 4, 1, 12, 0);

        sut.addComment(text, author, now);

        assertThat(sut.getComments()).hasSize(1);
        Comment comment = sut.getComments().getFirst();
        assertThat(comment.text()).isEqualTo(text);
        assertThat(comment.author()).isEqualTo(author);
        assertThat(comment.createdAt()).isEqualTo(now);
    }

    /**
     * 상태 기반 테스트를 단축하는 또 다른 방법으로, 검증 대상 클래스의 동등 멤버를 정의할 수 있다.
     * 본질적으로 클래스가 값에 해당하고 값 객체로 변환할 수 있을 때만 효과적이다.
     */
    @Test
    void adding_a_comment_to_an_article3() {
        Article sut = new Article();
        Comment comment = new Comment(
                "Comment text",
                "John Doe",
                LocalDateTime.of(2019, 4, 1, 12, 0)
        );

        sut.addComment(comment.text(), comment.author(), comment.createdAt());

        assertThat(sut.getComments())
                .hasSize(1)
                .contains(comment);
    }

}