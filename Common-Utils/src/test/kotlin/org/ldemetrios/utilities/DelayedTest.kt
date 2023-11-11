@file:Suppress("UnusedImport")

package org.ldemetrios.utilities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.ldemetrios.utilities.Delayed
import org.ldemetrios.utils.testing.Assertions_assertMatches

class DelayedTest {
    @Test
    fun `Execution times`() {
        var executed = 0
        val delayed = delayed {
            executed++
            "abc"
        }
        assertEquals(0, executed)
        Assertions_assertMatches(Regex("Uninitialized Delayed 0x[0-9a-f]{1,8}"), delayed.toString())
        val value = delayed.get()
        assertEquals(1, executed)
        assertEquals("abc", value)
        assertEquals("Delayed[abc]", delayed.toString())
        val value2 = delayed.get()
        assertEquals(1, executed)
        assertEquals("abc", value2)
        assertEquals("Delayed[abc]", delayed.toString())
    }

    @Test
    fun `Actually delayed`() {
        var external = "abc"
        val delayed = delayed {
            external
        }
        Assertions_assertMatches(Regex("Uninitialized Delayed 0x[0-9a-f]{1,8}"), delayed.toString())
        external = "def"
        val value = delayed.get()
        assertEquals("def", value)
        assertEquals("Delayed[def]", delayed.toString())
    }
}