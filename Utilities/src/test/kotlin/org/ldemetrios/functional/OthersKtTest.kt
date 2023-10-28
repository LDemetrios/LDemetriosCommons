package org.ldemetrios.functional

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class OthersKtTest {

    @Test
    fun cons() {
        assertEquals(listOf(1, 2, 3, 4), cons(1, listOf(2, 3, 4)))
    }

    @Test
    fun first() {
        assertEquals(1, first(listOf(1, 2, 3, 4)))
    }

    @Test
    operator fun next() {
        assertEquals(listOf(2, 3, 4), next(listOf(1, 2, 3, 4)))
        assertEquals(listOf(2, 3), next(listOf(1, 2, 3)))
        assertEquals(listOf(2), next(listOf(1, 2)))
        assertEquals(null, next(listOf(1)))
        assertEquals(null, next(listOf<Int>()))
    }

    @Test
    fun rest() {
        assertEquals(listOf(2, 3, 4), rest(listOf(1, 2, 3, 4)))
        assertEquals(listOf(2, 3), rest(listOf(1, 2, 3)))
        assertEquals(listOf(2), rest(listOf(1, 2)))
        assertEquals(listOf<Int>(), rest(listOf(1)))
        assertEquals(listOf<Int>(), rest(listOf<Int>()))
    }

    @Test
    fun last() {
        assertEquals(4, last(listOf(1, 2, 3, 4)))
        assertEquals(3, last(listOf(1, 2, 3)))
        assertEquals(2, last(listOf(1, 2)))
        assertEquals(1, last(listOf(1)))
        assertEquals(null, last(listOf<Int>()))
    }

    @Test
    fun butlast() {
        assertEquals(3, butlast(listOf(1, 2, 3, 4)))
        assertEquals(2, butlast(listOf(1, 2, 3)))
        assertEquals(1, butlast(listOf(1, 2)))
        assertEquals(null, butlast(listOf(1)))
        assertEquals(null, butlast(listOf<Int>()))
        assertEquals(3, butlast(Iterable { listOf(1, 2, 3, 4).iterator() }))
        assertEquals(2, butlast(Iterable { listOf(1, 2, 3).iterator() }))
        assertEquals(1, butlast(Iterable { listOf(1, 2).iterator() }))
        assertEquals(null, butlast(Iterable { listOf(1).iterator() }))
        assertEquals(null, butlast(Iterable { listOf<Int>().iterator() }))
    }
}
