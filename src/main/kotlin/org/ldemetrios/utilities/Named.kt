@file:Suppress("unused")

package org.ldemetrios.utilities

data class Named<T>(val name: String, val value: T) {
    override fun toString(): String = name
}

fun <T> T.withName(name:String) = Named(name, this)

