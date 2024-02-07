package org.ldemetrios.functional

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PartialTest {
    @Test
    fun get() {
        assertEquals(listOf(2,4), List<Int>::filter[U, {it % 2 == 0}](listOf(1,2,3,4,5)))
    }
}
