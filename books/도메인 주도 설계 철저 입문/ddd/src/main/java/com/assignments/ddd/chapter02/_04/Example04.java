package com.assignments.ddd.chapter02._04;

record FullName(
        String firstName,
        String lastName
) {

}

public class Example04 {

    public static void main(String[] args) {
        FullName fullName = new FullName("masanobu", "naruse");
        System.out.println(fullName.lastName());
    }
}
