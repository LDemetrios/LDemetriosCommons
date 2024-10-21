package org.ldemetrios.parsers

import io.kotest.core.spec.style.FreeSpec
import org.ldemetrios.utilities.*
import kotlin.test.Test

class `Choice parser test` : FreeSpec({
    val NUMBER = RegexStr("[0-9]+").useNamed("value.toInt()") { value.toIntOrNull() }
    val WS = RegexStr("\\s*")
    val IDENTIFIER = RegexStr("[a-zA-Z]+").use { value }
    val UNDERSCORE = LiteralStr("_")
    val `!` = CutParser

    "Basic choice" {
        expectParse(
            NUMBER + IDENTIFIER,
            "abc12",
            "abc",
            "12",
        )
    }

    "Sequence fall back" {
        expectParse(
            NUMBER * UNDERSCORE * NUMBER + NUMBER * IDENTIFIER,
            "123abc",
            Dyad(123, "abc"),
            "",
        )
    }

    "None of the options" {
        expectedError(
            NUMBER + IDENTIFIER,
            "_123abc",
            ErrorLevel.Unpresent
        )
    }

    "More than one option returns first" {
        expectParse(
            NUMBER * IDENTIFIER + NUMBER * UNDERSCORE * NUMBER,
            "123abc123",
            Dyad(123, "abc"),
            "123",
        )
    }

    "Malformation check"() {
        expectedError(
            -LiteralStr("fun") * -WS * `!` * IDENTIFIER * LiteralStr("()") +
                    -LiteralStr("val") * -WS * `!` * IDENTIFIER * -WS * LiteralStr(":") * -WS * IDENTIFIER +
                    -LiteralStr("class") * -WS * `!` * IDENTIFIER,
            "val 123: X",
            ErrorLevel.Malformed
        )
    }

    "Cuts affects errors only" {
        expectParse(
            -LiteralStr("fun") * -WS * `!` * IDENTIFIER * LiteralStr("()") +
                    -LiteralStr("val") * -WS * `!` * IDENTIFIER * -WS * -LiteralStr(":") * -WS * IDENTIFIER +
                    -LiteralStr("class") * -WS * `!` * IDENTIFIER,
            "val x: X",
            Dyad("x", "X"),
            "",
        )
    }
})