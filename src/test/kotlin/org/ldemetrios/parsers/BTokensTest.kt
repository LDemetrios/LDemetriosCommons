package org.ldemetrios.parsers

import io.kotest.core.spec.style.FreeSpec
import org.ldemetrios.utilities.named
import kotlin.test.Test

class `Token parsers test` : FreeSpec({
    "Literals" - {
        "Arrays" - {
            "Success" {
                expectParse(
                    Literal(listOf(1, 2, 3)),
                    listOf(1, 2, 3, 4, 5),
                    listOf(1, 2, 3),
                    listOf(4, 5)
                )
            }

            "Failure with EOF" {
                expectedError(
                    Literal(listOf(1, 2, 3)),
                    listOf(1, 2),
                    ErrorLevel.Unpresent
                )
            }
            "Failure with missing" {
                expectedError(
                    Literal(listOf(1, 2, 3)),
                    listOf(1, 3, 2, 4, 5),
                    ErrorLevel.Unpresent
                )
            }
            "Long errors check" {
                expectedError(
                    Literal((1..25).toList()),
                    (1..23).toList() + (25..100).toList(),
                    ErrorLevel.Unpresent
                )
            }
        }

        "Strings" - {
            "Success" {
                expectParse(
                    LiteralStr("abac"),
                    "abacaba",
                    "abac",
                    "aba"
                )
            }
            "Failure with EOF" {
                expectedError(
                    LiteralStr("abacaba"),
                    "abac",
                    ErrorLevel.Unpresent
                )
            }
            "Failure with missing" {
                expectedError(
                    LiteralStr("acab"),
                    "abacaba",
                    ErrorLevel.Unpresent
                )
            }
            "Long errors check" {
                expectedError(
                    LiteralStr("acab"),
                    "abacabadabacabaeabacabadabacaba",
                    ErrorLevel.Unpresent
                )
            }
        }
    }


    "Regexes" - {
        "Success" {
            expectParse(
                RegexStr("a[bc]a[bc]"),
                "abacaba",
                named("value == abac") { it.value == "abac" },
                "aba",
                ignoreResultsDifferences = true
            )
        }
        "Greediness" {
            expectParse(
                RegexStr("[0-9]+"),
                "12a34b",
                named("value == 12") { it.value == "12" },
                "a34b",
                ignoreResultsDifferences = true
            )
        }
        "Failure" {
            expectedError(
                RegexStr("[0-9]+"),
                "a12a34b",
                ErrorLevel.Unpresent,
                ignoreResultsDifferences = true
            )
        }
        "Potential EOF" {
            expectParse(
                RegexStr("[0-9]+"),
                "1234",
                named("value == 1234") { it.value == "1234" },
                "",
                ignoreResultsDifferences = true
            )
        }
        "Long failure" {
            expectedError(
                RegexStr("[0-9]+"),
                "a12a34b56c78daaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                ErrorLevel.Unpresent,
                ignoreResultsDifferences = true
            )
        }
    }
})