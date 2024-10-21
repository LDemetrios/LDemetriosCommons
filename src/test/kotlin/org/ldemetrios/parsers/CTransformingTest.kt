package org.ldemetrios.parsers

import io.kotest.core.spec.style.FreeSpec
import kotlin.test.Test

class `Transforming parsers test` : FreeSpec({
    "Transforming" {
        expectParse(
            RegexStr("[0-9]+") use { value.toInt() },
            "123abc",
            123,
            "abc"
        )
    }

    "Filtering" {
        expectedError(
            RegexStr("[0-9]+") requiring { it.value.toInt() > 100 },
            "12ab",
            ErrorLevel.Malformed
        )
    }
    "Both" {
        expectedError(
            RegexStr("[0-9]+") useNN { if(value.length > 2) null else value.toInt()},
            "123ab",
            ErrorLevel.Unpresent
        )
    }
})