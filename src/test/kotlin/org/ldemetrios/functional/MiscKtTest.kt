package org.ldemetrios.functional

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class `MiscKt Test` : FreeSpec({
    "cons" {
        cons(1, listOf(2, 3, 4)) shouldBe listOf(1, 2, 3, 4)
    }

    "first" {
        first(listOf(1, 2, 3, 4)) shouldBe 1
    }

    "next" {
        next(listOf(1, 2, 3, 4)) shouldBe listOf(2, 3, 4);
        next(listOf(1, 2, 3)) shouldBe listOf(2, 3);
        next(listOf(1, 2)) shouldBe listOf(2);
        next(listOf(1)) shouldBe null
        next(listOf<Int>()) shouldBe null
    }

    "rest" {
        rest(listOf(1, 2, 3, 4)) shouldBe listOf(2, 3, 4);
        rest(listOf(1, 2, 3)) shouldBe listOf(2, 3);
        rest(listOf(1, 2)) shouldBe listOf(2);
        rest(listOf(1)) shouldBe listOf<Int>();
        rest(listOf<Int>()) shouldBe listOf<Int>();
    }

    "last" {
        last(listOf(1, 2, 3, 4)) shouldBe 4
        last(listOf(1, 2, 3)) shouldBe 3
        last(listOf(1, 2)) shouldBe 2
        last(listOf(1)) shouldBe 1
        last(listOf<Int>()) shouldBe null
    }

    "butlast" {
        butlast(listOf(1, 2, 3, 4)) shouldBe 3
        butlast(listOf(1, 2, 3)) shouldBe 2
        butlast(listOf(1, 2)) shouldBe 1
        butlast(listOf(1)) shouldBe null
        butlast(listOf<Int>()) shouldBe null

        butlast(Iterable { listOf(1, 2, 3, 4).iterator() }) shouldBe 3
        butlast(Iterable { listOf(1, 2, 3).iterator() }) shouldBe 2
        butlast(Iterable { listOf(1, 2).iterator() }) shouldBe 1
        butlast(Iterable { listOf(1).iterator() }) shouldBe null
        butlast(Iterable { listOf<Int>().iterator() }) shouldBe null
    }
})
