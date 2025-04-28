package com.example.unittesting.chapter04.listing01;

public class HeaderRenderer implements IRenderer {

    @Override
    public String render(Message message) {
        return "<header>" + message.header() + "</header>";
    }
}
