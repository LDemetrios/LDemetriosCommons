package org.ldemetrios.parsers

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.ldemetrios.utilities.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.test.Test

inline fun <C, reified T> parserType(parser: Parser<C, T>) = ParserType(typeOf<T>())

data class ParserType(val itsType: KType) {
    inline fun <reified R> shouldBe() {
        itsType shouldBe typeOf<R>()
    }
}

class `Sequential parser test` : FreeSpec({
    val NUMBER = RegexStr("[0-9]+").useNamed("value.toInt()") { value.toIntOrNull() }
    val WS = RegexStr("\\s*")
    val IDENTIFIER = RegexStr("[a-z]+").use { value }
    val `!` = CutParser

    "Basic sequential test" {
        val parser = NUMBER * IDENTIFIER * NUMBER

        parserType(parser).shouldBe<Triad<Any?, Int?, String, Int?>>()

        expectParse(
            parser,
            "123abc456",
            Triad(123, "abc", 456),
            "",
        )
    }

    "No junk returned" {
        val parser = NUMBER * -WS * IDENTIFIER * -WS * NUMBER

        parserType(parser).shouldBe<Triad<Any?, Int?, String, Int?>>()

        expectParse(
            NUMBER * -WS * IDENTIFIER * -WS * NUMBER,
            "123  abc\n456",
            Triad(123, "abc", 456),
            "",
        )
    }

    "Big tuples support" {
        val parser = NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS

        parserType(parser).shouldBe<Dodecad<Int?, Int?, Int?, Int?, Int?, Int?, Int?, Int?, Int?, Int?, Int?, Int?, Int?>>()

        expectParse(
            NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                    NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                    NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS,
            "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15",
            Dodecad(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            "13 14 15",
        )
    }

    "Even bigger tuples support" {
        val parser = NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS

        parserType(parser).shouldBe<Myriad<Int?>>()

        expectParse(
            NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                    NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                    NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                    NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                    NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS *
                    NUMBER * -WS * NUMBER * -WS * NUMBER * -WS * NUMBER * -WS,
            "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25",
            Myriad((1..24).toList()),
            "25",
        )
    }

    "Erroneous" {
        expectedError(
            IDENTIFIER * -WS * NUMBER,
            "abc abc",
            ErrorLevel.Unpresent
        )
    }

    "Cut" - {
        "Success" {
            expectParse(
                -LiteralStr("fun") * -WS * `!` * IDENTIFIER,
                "fun abc 456",
                Monad("abc"),
                " 456",
            )
        }

        "Error before cut" {
            expectedError(
                -LiteralStr("fun") * -WS * `!` * IDENTIFIER,
                "abc abc 456",
                ErrorLevel.Unpresent
            )
        }

        "Error after cut" {
            expectedError(
                -LiteralStr("fun") * -WS * `!` * IDENTIFIER,
                "fun 123 456",
                ErrorLevel.Malformed
            )
        }
    }
})
