@file:Suppress("unused")

package org.ldemetrios.utilities

fun String.replaceAll(map: Map<String, String>): String {
    var res = this
    for ((key, value) in map) res = res.replace(key, value)
    return res
}

fun String.replaceAll(map: List<Pair<String, String>>): String {
    var res = this
    for ((key, value) in map) {
        res = res.replace(key, value)
    }
    return res
}