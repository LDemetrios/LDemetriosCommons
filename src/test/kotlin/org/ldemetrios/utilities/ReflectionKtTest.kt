package org.ldemetrios.utilities

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

fun <T> Assertions_assertEqualLists(a: List<T>, b: List<T>) = b shouldBe a

class `ReflectionKt Test` : FreeSpec({
    "superclassList" {
        LinkedHashMap::class.java.superclassList() shouldBe listOf(
            LinkedHashMap::class.java,
            HashMap::class.java,
            java.util.AbstractMap::class.java,
            Object::class.java
        )
        Integer::class.java.superclassList() shouldBe listOf(
            Integer::class.java,
            Number::class.java,
            Object::class.java
        )
        String::class.java.superclassList() shouldBe listOf(String::class.java, Object::class.java)
        Object::class.java.superclassList() shouldBe listOf(Object::class.java)
        Int::class.java.superclassList() shouldBe listOf(Int::class.java)
    }

    "juniorestCommonSuperclass" {
        juniorestCommonSuperclass(Integer::class.java, String::class.java) shouldBe Object::class.java

        juniorestCommonSuperclass(
            Integer::class.java,
            java.lang.Double::class.java
        ) shouldBe java.lang.Number::class.java

        juniorestCommonSuperclass(Int::class.java, String::class.java) shouldBe null

        juniorestCommonSuperclass(Nothing::class.java, String::class.java) shouldBe String::class.java
    }
})