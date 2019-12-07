package com.adedom.theegggame

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

//fun main() {

//    val myFunction: () -> Unit = { println("Hello Function") }
//    myFunction() // Hello Function

//    val biFunction: (Int, String) -> String = { i: Int, s: String ->
//        "2 parameters, Int = $i, String = $s"
//    }
//    println(biFunction(5, "Hola"))
//    println(biFunction.invoke(18, "Wow"))

//    val fun1: (Int) -> Unit = { println("it is an Int with value $it") }
//    val fun2: (String) -> Unit = {
//        println("it is a String with value $it")
//    }
//    println(fun1(38))
//    println(fun2("Hello Function"))

////////////////////////////////////////////////////////////////

//    fun getStringFromNetwork(callback: (String) -> Unit) {
//        val string: String = "String from network"
//        callback(string)
//    }
//
//    getStringFromNetwork { println(it) }

//    fun introduce(name: String, sentence: (String) -> String): String = sentence(name)
//
//    val message = introduce("Travis P") { "Hi, my name is $it." }
//    println(message)
//}

//data class Muggle(
//    var name: String,
//    var age: Int
//)
//
//fun stupidCount(list: List<Muggle>): Int = list.count { it.age == 25 }
//
//fun stupidLogString(list: List<Muggle>): String = list.joinToString { it.name }

