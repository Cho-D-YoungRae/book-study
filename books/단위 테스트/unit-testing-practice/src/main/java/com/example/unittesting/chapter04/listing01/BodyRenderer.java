package com.example.unittesting.chapter04.listing01;

public class BodyRenderer implements IRenderer {

    @Override
    public String render(Message message) {
        return "<body>" + message.body() + "</body>";
    }
}
