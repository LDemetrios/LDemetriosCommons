package org.ldemetrios.utilities

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.ldemetrios.utils.testing.Assertions_assertEqualLists

class ReflectionKtTest {
    @Test
    fun superclassList() {
        assertEquals(LinkedHashMap::class.java, LinkedHashMap::class.java)
        Assertions_assertEqualLists(
            listOf(LinkedHashMap::class, HashMap::class.java, java.util.AbstractMap::class.java, Object::class.java),
            LinkedHashMap::class.java.superclassList()
        )
    }

    @Test
    fun juniorestCommonSuperclass() {
        assertEquals(
            Object::class.java,
            juniorestCommonSuperclass(Integer::class.java, String::class.java)
        )
    }
}