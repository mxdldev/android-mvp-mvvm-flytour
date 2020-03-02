package com.fly.tour.test.anonymity

/**
 * Description: <Anonymity><br></br>
 * Author:      mxdl<br></br>
 * Date:        2020/2/24<br></br>
 * Version:     V1.0.0<br></br>
 * Update:     <br></br>
</Anonymity> */
//1.标准写法
fun add(x: Int, y: Int): Int {
    return x + y
}
fun add1(x: Int, y: Int): Int = x + y
//====================================
//2.匿名写法
var add2 = fun(x: Int, y: Int): Int {
    return x + y
}
var add3 = fun(x: Int, y: Int): Int = x + y
//=====================================
//3.Lambda写法
var add4: (Int, Int) -> Int = { x, y -> x * y }
fun main(args: Array<String>) {
    println(add1(1, 2))
}
