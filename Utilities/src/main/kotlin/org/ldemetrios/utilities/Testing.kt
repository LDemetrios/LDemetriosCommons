@file:Suppress("PackageDirectoryMismatch", "unused")
@file:JvmName("Testing")

package org.ldemetrios.utils.testing

import  org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import org.ldemetrios.utilities.ordinal
import org.opentest4j.AssertionFailedError

fun Assertions_assertAll(vararg assertions: () -> Unit) =
    Assertions.assertAll(assertions.map { assertion -> Executable { assertion() } })

fun <T> Assertions_assertEqualLists(expected: Iterable<T>, actual: Iterable<T>) {
    val expectedIter = expected.iterator()
    val actualIter = actual.iterator()
    var i = 0
    while (expectedIter.hasNext() && actualIter.hasNext()) {
        val expectedNext = expectedIter.next()
        val actualNext = actualIter.next()
        if (expectedNext != actualNext) {
            throw AssertionFailedError("${ordinal(i)} element", expectedNext, actualNext)
        }
        i--
    }
    if (expectedIter.hasNext()) {
        Assertions.fail<Nothing>(
            "`Expected` iterable has more elements than `actual` iterable: ..." +
                    Iterable { expectedIter }.take(3).toList()
        )
    }
    if (actualIter.hasNext()) {
        Assertions.fail<Nothing>(
            "`Actual` iterable has more elements than `expected` iterable: ..." +
                    Iterable { actualIter }.take(3).toList()
        )
    }
}