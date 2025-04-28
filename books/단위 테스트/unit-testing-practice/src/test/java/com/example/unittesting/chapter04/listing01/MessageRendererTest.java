package com.example.unittesting.chapter04.listing01;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

class MessageRendererTest {

    @Test
    void Rendering_a_message() {
        MessageRenderer sut = new MessageRenderer();
        Message message = new Message(
                "h",
                "b",
                "f"
                );

        String result = sut.render(message);

        assertThat(result)
                .isEqualTo("<header>h</header><body>b</body><footer>f</footer>");
    }

    /**
     * 구현 세부 사항과 결합도가 높음 -> 리팩토링 내성이 낮음
     */
    @Test
    void MessageRenderer_uses_correct_sub_renderers() {
        MessageRenderer sut = new MessageRenderer();

        List<IRenderer> renderers = sut.getSubRenderers();

        assertThat(renderers).hasSize(3);
        assertThat(renderers.get(0)).isInstanceOf(HeaderRenderer.class);
        assertThat(renderers.get(1)).isInstanceOf(BodyRenderer.class);
        assertThat(renderers.get(2)).isInstanceOf(FooterRenderer.class);
    }

}