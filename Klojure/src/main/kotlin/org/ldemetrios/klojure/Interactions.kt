@file:Suppress("unused")

package org.ldemetrios.klojure

import clojure.lang.*
import java.lang.UnsupportedOperationException

fun <T> Any.asList(): List<T> =
    when (this) {
        is PersistentVector -> this.seq().toIterable<T>().toList() // TO DO
        is ISeq -> this.toIterable<T>().toList()
        else -> throw UnsupportedOperationException()
    }

fun <T> Any?.declojurize(): T =
    when (this) {
        null -> null
        is PersistentVector -> this.seq().toIterable<T>().toList() // TO DO
        is ISeq -> this.toIterable<T>().toList()
        else -> throw UnsupportedOperationException()
    } as T

fun <T> Any?.clojurize(): Any? =
    when (this) {
        null -> null
        is List<*> -> PersistentVector.create(this)
        else -> throw UnsupportedOperationException()
    }

fun <T> ISeq?.toIterable() = Iterable {
    object : Iterator<T> {
        var x: ISeq? = this@toIterable
        override fun hasNext(): Boolean = x != null

        override fun next(): T {
            val ret = x!!.first()
            x = x!!.next()
            return ret as T
        }
    }
}

val `{}`: IPersistentMap = PersistentHashMap.EMPTY
val `()`: IPersistentList = PersistentList.create(listOf<Any?>())

object `'` {
    operator fun invoke(vararg args: Any?) = PersistentList.create(args.toList())
    operator fun get(vararg args: Any?) = PersistentVector.create(*args)
}

fun main() {
    println(`'`(1, 2, 3, 4))
    println(`'`[1, 2, 3, 4])
}
