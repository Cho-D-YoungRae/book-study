package com.example.unittesting.chapter04.listing01;

public class FooterRenderer implements IRenderer {

    @Override
    public String render(Message message) {
        return "<footer>" + message.footer() + "</footer>";
    }
}
