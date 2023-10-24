package org.ldemetrios.js

import java.lang.StringBuilder
import java.text.ParseException

class JSParser(val string: String) {
    var pos = 0

    private inline fun checkNotEnd() {
        if (pos >= string.length) throw ParseException("Unexpected EOF", pos)
    }

    private fun expect(c: Char) {
        if (pop() != c) throw ParseException("Expected $c", pos - 1)
    }

    private fun expect(string: String) {
        val from = pos
        var index = 0
        for (c in string) {
            if (peek() != c) throw ParseException("Expected $string, not ${string.take(index)}$c...", from)
            index++
        }
    }

    private fun <T> expectAs(string: String, value: T): T {
        expect(string)
        return value
    }

    private fun peek(): Char {
        checkNotEnd()
        return string[pos]
    }

    private fun pop(): Char {
        checkNotEnd()
        return string[pos++]
    }

    private fun skipWs() {
        while (pos < string.length && Character.isWhitespace(peek())) pop()
    }

    private fun parseString(): String {
        val sb = StringBuilder()
        while (peek() != '"') {
            val next = pop()
            if (next == '\\') {
                sb.append(
                    when (val c = pop()) {
                        '\\' -> '\\'
                        '/' -> '/'
                        '"' -> '\"'
                        'b' -> '\b'
                        'f' -> '\u000C'
                        'n' -> '\n'
                        'r' -> '\r'
                        't' -> '\t'
                        else -> throw ParseException("Illegal escape '\\$c'", pos - 1)
                    }
                )
            } else sb.append(next)
        }
        return sb.toString()
    }

    private fun parseValue(): JSStuff {
        skipWs()
        val ret = when (peek()) {
            'n' -> expectAs("null", JSNull)
            't' -> expectAs("true", JSTrue)
            'f' -> expectAs("false", JSFalse)
            'u' -> expectAs("undefined", JSUndefined)
            '"' -> {
                pop()
                val s = parseString()
                expect('"')
                JSString(s)
            }

            '{' -> {
                pop()
                val o = parseObject()
                expect('}')
                o
            }

            '[' -> {
                pop()
                val o = parseArray()
                expect(']')
                o
            }

            '.', in '0'..'9' -> JSNumber(parseNumber())
            else -> throw ParseException("Unexpected symbol ${peek()}", pos)
        }
        skipWs()
        return ret
    }

    private fun parseArray(): JSArray {
        val res = JSArray()
        skipWs()
        if (peek() == ']') return res
        res.add(parseValue())
        while (peek() != ']') {
            expect(',')
            res.add(parseValue())
        }
        return res
    }

    private fun parseObject(): JSObject {
        val res = JSObject()
        skipWs()
        if (peek() == '}') return res
        expect('"')
        val key = parseString()
        expect('"')
        skipWs()
        expect(':')
        res[key] = parseValue()
        while (peek() != '}') {
            expect(',')
            skipWs()
            expect('"')
            val key = parseString()
            expect('"')
            skipWs()
            expect(':')
            res[key] = parseValue()
        }
        return res
    }

    private fun parseNumber(): Double {
        val sb = StringBuilder()
        if (peek() == '-') sb.append(pop())
        while (pos < string.length && (peek() in '0'..'9')) sb.append(pop())
        if (pos < string.length && peek() == '.') {
            sb.append(pop())
            while (pos < string.length && (peek() in '0'..'9')) sb.append(pop())
        }
        if (pos < string.length && (peek() == 'e' || peek() == 'E')) {
            sb.append(pop())
            if (peek() == '-' || peek() == '+') sb.append(pop())
            while (pos < string.length && (peek() in '0'..'9')) sb.append(pop())
        }
        return sb.toString().toDouble()
    }

    companion object {
        fun parseValue(str: String): JSStuff {
            val p = JSParser(str)
            val value = p.parseValue()
            p.skipWs()
            if (p.pos < p.string.length) throw ParseException("Expected EOF", p.pos)
            return value
        }

        fun parseObject(str: String) = parseValue(str).cast<JSObject>()
        fun parseArray(str: String) = parseValue(str).cast<JSArray>()
        fun parseBoolean(str: String) = parseValue(str).cast<JSBoolean>()
        fun parseNumber(str: String) = parseValue(str).cast<JSNumber>()
        fun parseString(str: String) = parseValue(str).cast<JSString>()
    }

//    override fun toString(): String {
//        return string.substring(pos, min(pos + 15, string.length))
//    }
}
