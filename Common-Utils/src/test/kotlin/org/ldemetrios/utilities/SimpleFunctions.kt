@file:Suppress("unused")

package org.ldemetrios.utilities

fun inc(i:Int) = i + 1
fun dec(i:Int) = i - 1
fun square(i:Int) = i * i
fun cube(i:Int) = i * i * i

fun <T> List<T>.head() = first()
fun <T> List<T>.tail() = drop(1)

fun double(i:Int) = i * 2
fun triple(i:Int) = i * 3


