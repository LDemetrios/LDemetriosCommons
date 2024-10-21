@file:Suppress("UnusedImport")

package org.ldemetrios.utilities

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import org.junit.jupiter.api.Test

class `Delayed Test` : FreeSpec({
    "Execution times" {
        var executed = 0

        val delayed = delayed {
            executed++
            "abc"
        }
        executed shouldBe 0 // Never executed

        delayed.toString() shouldMatch Regex("Uninitialized Delayed 0x[0-9a-f]{1,8}")

        executed shouldBe 0 // Still didn't execute

        val value = delayed.get()

        executed shouldBe 1 // Now it did
        delayed.toString() shouldBe "Delayed[abc]"
        executed shouldBe 1 // Didn't repeat
        value shouldBe "abc"

        val value2 = delayed.get()
        executed shouldBe 1
        value2 shouldBe "abc"
        delayed.toString() shouldBe "Delayed[abc]"
    }

    "Actually delayed" {
        var external = "abc"

        val delayed = delayed {
            external
        }

        delayed.toString() shouldMatch Regex("Uninitialized Delayed 0x[0-9a-f]{1,8}")
        external = "def"
        val value = delayed.get()
        value shouldBe "def"
        delayed.toString() shouldBe "Delayed[def]"
    }
})