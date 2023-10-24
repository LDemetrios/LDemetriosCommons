package org.ldemetrios.utilities

data class Named<T>(val name: String, val value: T) {
    override fun toString(): String = name
}

