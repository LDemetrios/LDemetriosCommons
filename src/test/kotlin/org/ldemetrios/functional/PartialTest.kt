package org.ldemetrios.functional

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

import org.junit.jupiter.api.Assertions.*
import kotlin.reflect.typeOf


private inline fun <reified T> type(x:T) = typeOf<T>()

class `Partial Test` : FreeSpec({
    "get" {
        val f = List<Int>::filter[U, { it % 2 == 0 }] // Autoinferring type is important

        type(f) shouldBe typeOf<(List<Int>) -> List<Int>>()

        f(listOf(1, 2, 3, 4, 5)) shouldBe listOf(2, 4)
    }
})
