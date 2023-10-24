@file:Suppress("unused")

package org.ldemetrios.utilities

operator fun <T, R> ((T) -> R).unaryPlus(): T.() -> R = { this@unaryPlus(this) }
operator fun <T, R> (T.() -> R).unaryMinus(): (T) -> R = { it.this() }

fun <T, U> T.cast(): U = this as U

fun <T> T.alsoPrint(): T {
    println(this)
    return this
}

fun <T> T.alsoPrintln(): T {
    println(this)
    return this
}

infix fun <A, B, C> ((A) -> B).then(other: (B) -> C): (A) -> C = { other(this(it)) }
