package com.fly.tour.test.view

/**
 * Description: <Person1><br>
 * Author:      mxdl<br>
 * Date:        2020/2/20<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class Person {
    var username: String? = null
    var age: Int = 16
}

fun main(args: Array<String>) {
    var a = 10;
    println(a.isOuShu)
}
fun <T> MutableList<T>.swap(indexA: Int, indexB: Int) {
    val temp = this[indexA]
    this[indexA] = this[indexB]
    this[indexB] = temp
}
val Int.isOuShu: Boolean
    get() = this % 2 == 0
