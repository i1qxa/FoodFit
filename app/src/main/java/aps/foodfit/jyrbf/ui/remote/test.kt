package aps.foodfit.jyrbf.ui.remote

import java.lang.StringBuilder

fun main(){
    val input = readln()
    val crypted = StringBuilder()
    input.forEach {
        crypted.append(Char((it.code)+2))
    }
    println(crypted)
}