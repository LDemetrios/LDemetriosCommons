@file:Suppress("NonAsciiCharacters")

package org.ldemetrios.utilities

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows as jAssertThrows
import org.junit.jupiter.api.Assertions.*

class `ChainingKt Test` : FreeSpec({
    "((T) -> R).unaryPlus()" {
        1.(+::dec)() shouldBe 0
        (-1).(+::inc)() shouldBe 0
        1.(+::inc)().(+::triple)().(+::square)() shouldBe 36
    }

    "((T) -> R).unaryMinus()" {
        (-+::square)(6) shouldBe 36
    }

    "cast" - {
        val number: Number = 1
        "Type infers" {
            val x: Int = number.cast() // Auto-infer
            x shouldBe 1
        }
        "Type-checking" {
            shouldThrow<ClassCastException> {
                number.cast<String>()
            }
        }
        "Generics suppress type-checking" {
            number.castUnchecked<String>()

        }
    }

    "then" {
        (::inc then ::triple then ::square)(6) shouldBe 441
    }

    "applyIf" {
        6.applyIf(true, ::square) shouldBe 36
        6.applyIf(false, ::square) shouldBe 6
    }
})