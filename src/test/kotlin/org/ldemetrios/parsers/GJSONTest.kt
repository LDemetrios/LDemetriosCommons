package org.ldemetrios.parsers

import io.kotest.core.spec.style.FreeSpec
import org.ldemetrios.js.*
import org.ldemetrios.utilities.Monad
import kotlin.test.Test

val ws = regex("\\s*") named "ws"
val number = RegexStr("[+-]?[0-9]+(.[0-9]+)?([Ee][+-]?[0-9]+)?").use { value.toDouble() } named "number"

val quote = literallyAs("\\\"", "\"")
val backslash = literallyAs("\\\\", '\\')
val slash = literallyAs("\\/", '/')
val b = literallyAs("\\b", '\b')
val f = literallyAs("\\f", '\u000C')
val n = literallyAs("\\n", '\n')
val r = literallyAs("\\r", '\r')
val t = literallyAs("\\t", '\t')

val escape = (quote or backslash or slash or b or f or n or r or t named "escape").notrace() or
        (regex("\\\\u[0-9A-Fa-f]{4}") use {
            value.substring(2).toInt(16).toChar()
        } named "unicode escape")

val regular = RegexStr("[ -!#-\\[\\]-\\uFFFF]") use { value[0] } named "regular char"

val character = (regular or escape)

val characters = star(character).useNamed("joinToString(\"\")") { joinToString("") }

val string = (-"\"" * characters * -"\"" use unpack() named "string").notrace()

val element: Transforming<Char, Monad<JSStuff>, JSStuff> = -ws * Delayed(::value) * -ws use unpack()

val elements = (
        star(element * -opt(-",") * -ws use unpack()) or
                (ws.useNamed("listOf()") { listOf() })
        ) named "elements"

val array = -"[" * elements * -"]" use unpack() named "array"

val members = star(-ws * string * -":" * element * -opt(-",") * -ws) or (ws use { listOf() }) named "members"

val obj = (-"{" * members * -"}").useNamed("associate") { first.associate { it.first to it.second } } named "object"

val value = (string with ::JSString) or
        (number with ::JSNumber) or
        (array with ::JSArray) or
        (obj with ::JSObject) or
        literallyAs("true", JSTrue) or
        literallyAs("false", JSFalse) or
        literallyAs("null", JSNull) named "value"

class GJSONTest : FreeSpec({


    "Regular JSON" {
        expectParse(
            value,
            """
                {
                   "a": 1,
                   "b": 2.0
                }
            """.trimIndent(),
            JSObject("a" to 1.0.js, "b" to 2.0.js),
            ""
        )
    }

    fun arr(x: JSStuff) = JSArray(listOf(x))

    "Recursion" {
        expectParse(
            value,
            "[[[[]]]]",
            arr(arr(arr(JSArray()))),
            ""
        )
    }
})