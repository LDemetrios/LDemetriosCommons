package org.ldemetrios.utilities

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class NamingKtTest {

    @Test
    fun ordinal() {
        assertEquals("1-st", ordinal(1))
        assertEquals("2-nd", ordinal(2))
        assertEquals("3-rd", ordinal(3))
        assertEquals("4-th", ordinal(4))
        assertEquals("5-th", ordinal(5))
        assertEquals("6-th", ordinal(6))
        assertEquals("7-th", ordinal(7))
        assertEquals("8-th", ordinal(8))
        assertEquals("9-th", ordinal(9))
        assertEquals("10-th", ordinal(10))
        assertEquals("11-th", ordinal(11))
        assertEquals("12-th", ordinal(12))
        assertEquals("13-th", ordinal(13))
        assertEquals("14-th", ordinal(14))
        assertEquals("15-th", ordinal(15))
        assertEquals("16-th", ordinal(16))
        assertEquals("17-th", ordinal(17))
        assertEquals("18-th", ordinal(18))
        assertEquals("19-th", ordinal(19))
        assertEquals("20-th", ordinal(20))
        assertEquals("21-st", ordinal(21))
        assertEquals("22-nd", ordinal(22))
        assertEquals("23-rd", ordinal(23))
        assertEquals("24-th", ordinal(24))
        assertEquals("25-th", ordinal(25))
        assertEquals("26-th", ordinal(26))
        assertEquals("27-th", ordinal(27))
        assertEquals("28-th", ordinal(28))
        assertEquals("29-th", ordinal(29))
        assertEquals("30-th", ordinal(30))
        assertEquals("31-st", ordinal(31))
    }

    @Test
    fun naturallyJoin() {
        assertEquals("1, 2, 3 and 4", naturallyJoin(1, 2, 3, 4))
        assertEquals("1, 2 and 3", naturallyJoin(1, 2, 3))
        assertEquals("1 and 2", naturallyJoin(1, 2))
        assertEquals("1", naturallyJoin(1))
        assertEquals("none", org.ldemetrios.utilities.naturallyJoin())
    }
}