package org.ldemetrios.bash

import org.ldemetrios.utilities.Quadruple
import org.ldemetrios.utilities.juniorestCommonSuperclass

data class Type<T>(val clazz: Class<T>) {
    companion object {
        inline fun <reified T> of() = Type(T::class.java)

        fun ofNothing() = Type(Nothing::class.java)

        fun <C, A : C, B : C> common(a: Type<A>, b: Type<B>) = Type(juniorestCommonSuperclass(a.clazz, b.clazz) as Class<C>)
    }

    fun checkIs(instance: Any): Boolean = clazz.isInstance(instance)
}

fun main() {
    println(
        juniorestCommonSuperclass(
            UnsupportedOperationException::class.java,
            IllegalArgumentException::class.java,
        )
    )
}
