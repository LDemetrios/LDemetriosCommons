@file:Suppress("NonAsciiCharacters")

package org.ldemetrios.utilities

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows as jAssertThrows
import org.junit.jupiter.api.Assertions.*

class ChainingKtTest {

    @Test
    fun `((T) -〉 R),unaryPlus()`() {
        assertEquals(
            0,
            1.(+::dec)()
        )
        assertEquals(
            0,
            (-1).(+::inc)()
        )
        assertEquals(
            36,
            1.(+::inc)().(+::triple)().(+::square)()
        )
    }

    @Test
    fun `(T,() -〉 R),unaryMinus()`() {
        assertEquals(
            36,
            (-+::square)(6)
        )
    }

    @Test
    fun cast() {
        val number: Number = 1
        assertEquals(
            1, number.cast()
        )
        jAssertThrows<ClassCastException> { number.cast<String>() }
        assertDoesNotThrow {
            number.castUnchecked<String>()
        }
    }

    @Test
    fun then() {
        assertEquals(
            441,
            (::inc then ::triple then ::square)(6)
        )
    }

    @Test
    fun applyIf() {
        assertEquals(
            36,
            6.applyIf(true, ::square)
        )
        assertEquals(
            6,
            6.applyIf(false, ::square)
        )
    }
}