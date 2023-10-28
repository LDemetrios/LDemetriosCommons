@file:Suppress("unused")

package org.ldemetrios.js

@Deprecated(
    "May cause interesting special effects",
    ReplaceWith("explicit type-based conversion")
)
fun Any?.asJS(): JSStuff = when (this) {
    null -> JSNull
    true -> JSTrue
    false -> JSFalse
    is Number -> JSNumber(toDouble())
    is String -> JSString(this)
    is Map<*, *> -> JSObject.ofAny(mapKeys { it.toString() })
    is Iterable<*> -> JSArray.ofAnyList(this)
    else -> JSUndefined
}

interface JSStuff {
    fun toString(indent: Int): String {
        val sb = StringBuilder()
        appendTo(sb, indent, 0)
        return sb.toString()
    }

    fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int)

    operator fun get(ind: Int): JSStuff
    operator fun get(ind: String): JSStuff
    operator fun get(ind: Any?): JSStuff = get(ind.toString())

    fun toBoolean(): Boolean
    fun toInt(): Int = toDouble().toInt()
    fun toDouble(): Double
}

interface JSPrimitive : JSStuff {
    override fun get(ind: Int): JSStuff = JSUndefined
    override fun get(ind: String): JSStuff = JSUndefined
    override fun get(ind: Any?): JSStuff = JSUndefined
}

enum class JSBoolean(private val b: Boolean) : JSPrimitive {
    True(true), False(false);

    override fun toBoolean(): Boolean = b
    override fun toString(indent: Int): String = b.toString()
    override fun toDouble(): Double = if (b) 1.0 else 0.0
    override fun toString() = toString(0)
    override fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int) {
        sb.append(b)
    }

    companion object {
        fun of(b: Boolean) = if (b) JSTrue else JSFalse
    }
}

val JSTrue = JSBoolean.True
val JSFalse = JSBoolean.False

object JSUndefined : JSPrimitive {
    override fun toString(indent: Int): String = "undefined"
    override fun toBoolean(): Boolean = false
    override fun toDouble(): Double = 0.0
    override fun toString() = toString(0)
    override fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int) {
        sb.append("undefined")
    }
}

object JSNull : JSPrimitive {
    override fun toString(indent: Int): String = "null"
    override fun toBoolean(): Boolean = false
    override fun toDouble(): Double = 0.0
    override fun toString() = toString(0)
    override fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int) {
        sb.append("null")
    }
}

data class JSNumber(private val number: Double) : JSPrimitive {
    override fun toString(indent: Int): String = number.toString()
    override fun toBoolean(): Boolean = number != 0.0 && number != -0.0
    override fun toDouble(): Double = number
    override fun toString() = toString(0)
    override fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int) {
        sb.append(number.toString())
    }
}

internal fun String.escape() =
    replace("\\", "\\\\")
        .replace("/", "\\/")
        .replace("\"", "\\\"")
        .replace("\b", "\\b")
        .replace("\u000C", "\\f")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")

data class JSString(private val str: String) : JSStuff {
    override fun appendTo(sb: StringBuilder, indent: Int, curIndent: Int) {
        sb.append('"')
        sb.append(            str.escape()        )
        sb.append('"')
    }

    override fun get(ind: Int): JSStuff = if (ind in str.indices) JSString("" + str[ind]) else JSUndefined
    override fun get(ind: String): JSStuff = ind
        .removeSuffix(".0")
        .toIntOrNull()
        ?.let { this[it] }
        ?: JSUndefined

    override fun toBoolean(): Boolean = str.isBlank() || str == "false" || str == "0"
    override fun toDouble(): Double = str.toDouble()
    override fun toString() = toString(0)
}

fun JSStuff.isBamboo(): Boolean = this is JSPrimitive || this is JSString
        || (this is JSArray && (this.size == 0 || this.size == 1 && this[0].isBamboo()))
        || (this is JSObject && (this.size == 0 || this.size == 1 && iterator().next().value.isBamboo()))

interface JSContainer : JSStuff {
    val size: Int

    operator fun set(ind: Int, value: JSStuff): JSStuff
    operator fun set(ind: String, value: JSStuff): JSStuff
    operator fun set(ind: Any?, value: JSStuff) = set(ind.toString(), value)
}

val Number.js get() = JSNumber(toDouble())
val Boolean.js get() = JSBoolean.of(this)
val String.js get() = JSString(this)
operator fun JSStuff?.get(ind: Int): JSStuff = if (this === null) JSNull else this[ind]
operator fun JSStuff?.get(ind: String): JSStuff = if (this === null) JSNull else this[ind]
operator fun JSStuff?.get(ind: Any?): JSStuff = if (this === null) JSNull else this[ind]
