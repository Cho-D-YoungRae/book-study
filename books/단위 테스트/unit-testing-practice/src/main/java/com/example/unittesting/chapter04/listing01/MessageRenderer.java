package com.example.unittesting.chapter04.listing01;

import java.util.List;
import java.util.stream.Collectors;

public class MessageRenderer implements IRenderer {

    private final List<IRenderer> subRenderers;

    public MessageRenderer() {
        this.subRenderers = List.of(
                new HeaderRenderer(),
                new BodyRenderer(),
                new FooterRenderer()
        );
    }

    @Override
    public String render(Message message) {
        return subRenderers.stream()
                .map(renderer -> renderer.render(message))
                .collect(Collectors.joining());
    }

    public List<IRenderer> getSubRenderers() {
        return subRenderers;
    }
}
