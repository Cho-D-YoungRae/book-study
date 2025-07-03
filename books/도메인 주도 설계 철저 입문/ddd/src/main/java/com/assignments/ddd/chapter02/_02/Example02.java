package com.assignments.ddd.chapter02._02;

public class Example02 {

    public static void main(String[] args) {
        String fullName = "naruse mananobu";
        String[] tokens = fullName.split(" ");
        String lastName = tokens[0];
        System.out.println(lastName);
    }
}
