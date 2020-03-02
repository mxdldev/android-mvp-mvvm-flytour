package com.fly.tour.test.closure

/**
 * Description: <Closure><br>
 * Author:      mxdl<br>
 * Date:        2020/2/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
//=========================
//1.函数嵌套
fun test1() {
    println("test1")
    fun test2() {
        println("test2")
    }
}
//===========================
//2.函数作为返回值
fun test2(x: Int): () -> Int {
    var a = 0
    return fun(): Int {
        a++
        return a + x
    }
}

fun test3(v: Int): (Int, Int) -> Int {
    var a = 1
    var b = 2
    return { x, y -> x + y + a + b + v}
}

fun main(args: Array<String>) {
    var test = test2(10)
    println(test())
    println(test())
    println(test())

    var test2 =test3(10)
    var result = test2(12,1)
    println(result)
}