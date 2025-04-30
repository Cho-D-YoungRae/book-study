package com.example.unittesting.chapter06.listing04_06;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Article {

    private final List<Comment> comments = new ArrayList<>();

    public void addComment(String text, String author, LocalDateTime createdAt) {
        comments.add(new Comment(text, author, createdAt));
    }

    public List<Comment> getComments() {
        return comments.stream().toList();
    }
}
