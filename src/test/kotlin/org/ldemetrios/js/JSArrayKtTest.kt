package org.ldemetrios.js

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.ldemetrios.testing.shouldFormatTo


class `JSArray Test` : FreeSpec({
    "Get/Set manipulations" - {
        fun gen() = JSArray(1.js, 2.js, 3.js);
        val array = gen()

        "Array access" {
            array[0] shouldBe JSNumber(1.0);
            array[1] shouldBe JSNumber(2.0)
            array[2] shouldBe JSNumber(3.0)
        }

        "Out of bounds access" {
            array[3] shouldBe JSUndefined

            @Suppress("KotlinConstantConditions")
            array[-1] shouldBe JSUndefined
        }

        "String index access" {
            array["-1"] shouldBe JSUndefined
            array["2"] shouldBe JSNumber(3.0)
        }

        "Array size" {
            array.size shouldBe 3
        }

        "Modifying array" {
            val array = gen()
            array[0] = JSNumber(4.0);
            array[0] shouldBe JSNumber(4.0)
            array[1] shouldBe JSNumber(2.0)
            array[2] shouldBe JSNumber(3.0)
        }

        "Array extends by set access" {
            val array = gen()
            array[3] = JSString("abc");
            array[3] shouldBe JSString("abc")
            array.size shouldBe 4
        }

        "Voids are filled with Undefined" {
            val array = gen()
            array[5] = JSNull
            array.size shouldBe 6
            array[4] shouldBe  JSUndefined
        }
    }

    "Basic stringification" - {
        val array = JSArray(1.js, JSTrue, JSNull);
        "Default" {
            array shouldFormatTo "[1.0, true, null]"
        }
        "In line" {
            array.toString(0) shouldFormatTo "[1.0, true, null]"
        }
        "Dense" {
            array.toString(-1) shouldFormatTo "[1.0,true,null]"
        }
        "Pretty print" {
            array.toString(4) shouldFormatTo """
                |[
                |    1.0,
                |    true,
                |    null
                |]
                """.trimMargin()
        }
    }

    "Stringifying nested structutes" - {
        val array = JSArray(1.js, 2.js, 3.js, "null".js, JSUndefined, JSArray(JSTrue, JSFalse), JSNull);
        "Default" {
            array shouldFormatTo "[1.0, 2.0, 3.0, \"null\", undefined, [true, false], null]"
        }
        "In line" {
            array.toString(0) shouldFormatTo "[1.0, 2.0, 3.0, \"null\", undefined, [true, false], null]"
        }
        "Dense" {
            array.toString(-1) shouldFormatTo "[1.0,2.0,3.0,\"null\",undefined,[true,false],null]"
        }
        "Pretty print" {
            array.toString(4) shouldFormatTo """
               |[
               |    1.0,
               |    2.0,
               |    3.0,
               |    "null",
               |    undefined,
               |    [
               |        true,
               |        false
               |    ],
               |    null
               |]
                """.trimMargin()
        }
    }

    "Stringifying empty" {
        val arr = JSArray()
        arr.toString() shouldBe "[]"
        arr.toString(0) shouldBe "[]"
        arr.toString(-1) shouldBe "[]"
    }

    "Stringifying empty nested" {
        val arr = JSArray(listOf( JSArray(listOf( JSArray()))))

        arr.toString() shouldBe "[[[]]]"
        arr.toString(0) shouldBe "[[[]]]"
        arr.toString(-1) shouldBe "[[[]]]"
    }
})

