package org.ldemetrios.parsers

import io.kotest.core.spec.style.FreeSpec
import kotlin.test.Test

class `Repeating parser test` : FreeSpec({
    val SINGLE_DIGIT = RegexStr("[0-9]").useNamed("value.toInt()") { value.toInt() }

    "Basic repeating test" {
        expectParse(
            star(SINGLE_DIGIT),
            "123abc456",
            listOf(1, 2, 3),
            "abc456",
        )
    }

    "Not enough" {
        expectedError(
            SINGLE_DIGIT.repeat(3, 5),
            "12abc",
            ErrorLevel.Unpresent,
        )
    }

    "Too many" {
        expectParse(
            SINGLE_DIGIT.repeat(3, 5),
            "12345678abc",
            listOf(1, 2, 3, 4, 5),
            "678abc",
        )
    }

    "Zero handling" {
        expectParse(
            SINGLE_DIGIT.repeat(0, 5),
            "abc",
            listOf(),
            "abc",
        )
    }

    "Error level propagation" {
        expectedError(
            (SINGLE_DIGIT.requiringNamed("it < 5") { it < 5 }).repeat(3, 5),
            "163abc",
            ErrorLevel.Malformed,
        )
    }
})
