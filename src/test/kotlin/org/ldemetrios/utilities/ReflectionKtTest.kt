package org.ldemetrios.utilities

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ReflectionKtTest {
    @Test
    fun superclassList() {
        Assertions_assertEqualLists(
            listOf(LinkedHashMap::class.java, HashMap::class.java, java.util.AbstractMap::class.java, Object::class.java),
            LinkedHashMap::class.java.superclassList()
        )
        Assertions_assertEqualLists(
            listOf(Integer::class.java, Number::class.java, Object::class.java),
            Integer::class.java.superclassList()
        )
        Assertions_assertEqualLists(
            listOf(String::class.java, Object::class.java),
            String::class.java.superclassList()
        )
        Assertions_assertEqualLists(
            listOf(Object::class.java),
            Object::class.java.superclassList()
        )
        Assertions_assertEqualLists(
            listOf(Int::class.java),
            Int::class.java.superclassList()
        )
    }
    @Test
    fun juniorestCommonSuperclass() {
        assertEquals(
            Object::class.java,
            juniorestCommonSuperclass(Integer::class.java, String::class.java)
        )
        assertEquals(
            java.lang.Number::class.java,
            juniorestCommonSuperclass(Integer::class.java, java.lang.Double::class.java)
        )
        assertEquals(
            null,
            juniorestCommonSuperclass(Int::class.java, String::class.java)
        )
        assertEquals(
            String::class.java,
            juniorestCommonSuperclass(Nothing::class.java, String::class.java)
        )
    }
}