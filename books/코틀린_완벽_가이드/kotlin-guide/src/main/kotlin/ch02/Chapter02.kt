package ch02

fun main() {
    practice02()
}

fun practice01() {
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    println(a + b)
}

fun practice02() {
    println(emptyArray<String>().contentDeepToString())
    println(arrayOf("hello", "world").contentDeepToString())
    println(arrayOf(1, 4, 9).contentDeepToString())
    println(Array(4) { (it + 1) * (it + 1) }.contentDeepToString())
}