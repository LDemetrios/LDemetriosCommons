@file:Suppress("UnusedImport")

package org.ldemetrios.utilities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.ldemetrios.utilities.Delayed

class DelayedTest {
    @Test
    fun `Execution times`() {
        var executed = 0
        val delayed = delayed {
            executed++
            "abc"
        }
        assertEquals(0, executed)
        println(delayed.toString())
        assertTrue(Regex("Uninitialized Delayed 0x[0-9a-f]{8}").matches(delayed.toString()))
        val value = delayed.get()
        assertEquals(1, executed)
        assertEquals(value, "abc")
        assertEquals(delayed.toString(), "Delayed[abc]")
        val value2 = delayed.get()
        assertEquals(1, executed)
        assertEquals(value2, "abc")
        assertEquals(delayed.toString(), "Delayed[abc]")
    }

    @Test
    fun `Actually delayed`() {
        var external = "abc"
        val delayed = delayed {
            external
        }
        assertTrue(Regex("Uninitialized Delayed 0x[0-9a-f]{8}").matches(delayed.toString()))
        external = "def"
        val value = delayed.get()
        assertEquals(value, "def")
        assertEquals(delayed.toString(), "Delayed[def]")
    }
}