package ch04

fun main() {
    val person = Person("first", "family")
    println(person.firstName)
    println(person.fullName)
}

class Person(val firstName: String, familyName: String) {

    val fullName = "$firstName $familyName"
}