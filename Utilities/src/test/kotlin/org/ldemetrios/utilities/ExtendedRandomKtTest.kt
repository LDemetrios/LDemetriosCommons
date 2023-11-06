package org.ldemetrios.utilities

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ExtendedRandomKtTest {
    @Test
    fun `Language strings contain distinct characters only`() {
        for ((lang, set) in ExtendedRandom.setsMap) {
            val nonDistinct = set
                .groupBy { it }
                .map { it.key to it.value.size }
                .filter { it.second > 1 }
            if (nonDistinct.isNotEmpty()) {
                Assertions.fail<Unit>(
                    "$lang letters contains not distinct symbols: " +
                            nonDistinct.joinToString(", ") {
                                "${it.first} ${it.second} times"
                            } +
                            ". You may replace it with \"" +
                            set.toList().distinct().joinToString("") + "\""
                )
            }
        }
    }

    @Test
    fun `String length ratio`() {
        val random = ExtendedRandom(ExtendedRandom::class.java)
        val map = mutableMapOf<Int, Int>()
        (0 until 81).map {
            random.randomString(ExtendedRandom.GREEK, 3, 1)
        }.forEach {
//            println(it)
            if (it.length !in map) map[it.length] = 0
            map[it.length] = map[it.length]!! + 1
        }
        for (key in map.keys.sorted()) {
            println("$key: ${map[key]}")
        }
    }

    @Test
    fun `Random stuff`() {
        val random = ExtendedRandom(ExtendedRandom::class.java)
        val stuff = random.randomStuff(2, 4, 2, 5)
        // Something strange happens inside map generation
    }
}