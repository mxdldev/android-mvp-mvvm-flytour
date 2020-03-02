package com.fly.tour.test.lambda

/**
 * Description: <Lambda><br>
 * Author:      mxdl<br>
 * Date:        2020/2/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
//1.无参数
fun print() { println("HelloWorld")}
var print1: () -> Unit = {println("HelloWorld1")}
var print2 = {println("HelloWorld2")}
//=============================================
//2.有参数加法
fun add(x: Int, y: Int): Int {return x + y}
var add1: (Int, Int) -> Int = { x, y -> x + y }
var add2 = { x: Int, y: Int -> x + y }
//=============================================
//3.作为函数参数
fun add3(x: Int, y: Int): Int {return x + y}
fun add4(a: Int, b: Int): Int {return a + b}
fun add5(x:Int,add6:(Int,Int)->Int):Int{
    return x + add6.invoke(2,3)
}
fun add7(x:Int,add8:(Int)->Boolean):Int{
    if(add8(x)){
        return x
    }else{
        return 0
    }
}
fun main(args: Array<String>) {
//    print()
//    print1()
//    print2()
//    var result = add(1, 2);
//    println("result:$result")
//    var result1 = add1(2, 3)
//    println("result1:$result1")
    var result = add4(3, add3(1, 2))
    println("result:$result")
    var result5 = add5(1,{x:Int,y:Int -> x*y})
    var result6 = add5(1,{x:Int,y:Int -> x-y})
    println("result5:"+result5)
    println("result6:"+result6)

    var arr = arrayOf(1,3,5,7,9)
    var arr1 = arr.filter { it < 7 }
    for(a in arr1){
        println(a)
    }
    println(arr.filter { it < 7 }.component2())

    var add81 = add7(10,{x:Int -> x > 0})
    var add82 = add7(10,{it > 0})
    println(add81)
}

