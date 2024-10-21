package org.ldemetrios.utilities

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class `NamingKt Test` : FreeSpec({

    "ordinal" {
        ordinal(1) shouldBe "1-st"
        ordinal(2) shouldBe "2-nd"
        ordinal(3) shouldBe "3-rd"
        ordinal(4) shouldBe "4-th"
        ordinal(5) shouldBe "5-th"
        ordinal(6) shouldBe "6-th"
        ordinal(7) shouldBe "7-th"
        ordinal(8) shouldBe "8-th"
        ordinal(9) shouldBe "9-th"
        ordinal(10) shouldBe "10-th"
        ordinal(11) shouldBe "11-th"
        ordinal(12) shouldBe "12-th"
        ordinal(13) shouldBe "13-th"
        ordinal(14) shouldBe "14-th"
        ordinal(15) shouldBe "15-th"
        ordinal(16) shouldBe "16-th"
        ordinal(17) shouldBe "17-th"
        ordinal(18) shouldBe "18-th"
        ordinal(19) shouldBe "19-th"
        ordinal(20) shouldBe "20-th"
        ordinal(21) shouldBe "21-st"
        ordinal(22) shouldBe "22-nd"
        ordinal(23) shouldBe "23-rd"
        ordinal(24) shouldBe "24-th"
        ordinal(25) shouldBe "25-th"
        ordinal(26) shouldBe "26-th"
        ordinal(27) shouldBe "27-th"
        ordinal(28) shouldBe "28-th"
        ordinal(29) shouldBe "29-th"
        ordinal(30) shouldBe "30-th"
        ordinal(31) shouldBe "31-st"
    }

    "naturallyJoin" {
        naturallyJoin(1, 2, 3, 4) shouldBe "1, 2, 3 and 4"
        naturallyJoin(1, 2, 3) shouldBe "1, 2 and 3"
        naturallyJoin(1, 2) shouldBe "1 and 2"
        naturallyJoin(1) shouldBe "1"
        naturallyJoin() shouldBe "none"
    }
})