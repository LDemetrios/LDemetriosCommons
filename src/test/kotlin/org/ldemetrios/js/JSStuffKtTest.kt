package org.ldemetrios.js

import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class `JSStuff Test` : FreeSpec({
    "Bamboo" - {
        "Primitives" - {
            withData<JSStuff>(
                { "$it is Bamboo" },
                listOf(
                    JSTrue, JSFalse,
                    JSUndefined, JSNull,
                    JSNumber(1.0),
                    JSString("abc"),
                    JSArray(JSTrue),
                )
            ) {
                it.isBamboo() shouldBe true
            }
        }

        "Arrays" - {
            withData<JSStuff>(
                { "$it is Bamboo" },
                listOf(
                    JSArray(JSNumber(1.0)),
                    JSArray(JSString("abc")),
                    JSArray(JSArray(JSTrue) as JSStuff),
                    JSArray(JSArray(JSFalse) as JSStuff),
                    JSArray(JSArray(JSUndefined) as JSStuff),
                    JSArray(JSArray(JSNull) as JSStuff),
                    JSArray(JSArray(JSNumber(1.0)) as JSStuff),
                )
            ) {
                it.isBamboo() shouldBe true
            }
            "Not" {
                JSArray(JSArray(), JSTrue).isBamboo() shouldBe false
            }

        }

        "Very nested" {
            fun arr(x: JSStuff) = JSArray(x)
            arr(arr(arr(arr(arr(arr(arr(1.js))))))).isBamboo() shouldBe true
            JSObject("a" to JSObject("b" to JSObject("c" to JSObject()))).isBamboo() shouldBe true
        }

        "Bamboo spreads" {
            JSArray(
                JSArray(
                    JSArray(
                        JSArray(
                            JSTrue,
                            JSFalse
                        ) as JSStuff
                    ) as JSStuff
                ) as JSStuff
            ).isBamboo() shouldBe false

            JSObject(
                "abc" to JSArray(
                    JSObject(
                        "def" to JSArray(JSArray(JSTrue, JSFalse) as JSStuff)
                    ) as JSStuff
                )
            ).isBamboo() shouldBe false
        }

    }

    "JSBoolean" - {
        "Conversions" {
            JSTrue.toBoolean() shouldBe true
            JSTrue.toString() shouldBe "true"
            JSTrue.toDouble() shouldBe 1.0
            JSFalse.toBoolean() shouldBe false
            JSFalse.toString() shouldBe "false"
            JSFalse.toDouble() shouldBe 0.0
            JSBoolean.of(true).toString(4) shouldBe "true"
            JSBoolean.of(false).toString(4) shouldBe "false"
        }

        "JSBoolean.of" {
            JSBoolean.of(true) shouldBe JSTrue
            JSBoolean.of(false) shouldBe JSFalse
        }

        "Not indexable" {
            JSTrue[0] shouldBe JSUndefined
            JSFalse[0] shouldBe JSUndefined
            JSTrue["abc"] shouldBe JSUndefined
            JSFalse["abc"] shouldBe JSUndefined
            JSTrue[JSTrue] shouldBe JSUndefined
            JSFalse[JSUndefined] shouldBe JSUndefined
        }
    }

    "JSUndefined" - {
        "Conversions" {
            JSUndefined.toString() shouldBe "undefined"
            JSUndefined.toBoolean() shouldBe false
            JSUndefined.toDouble() shouldBe 0.0
            JSUndefined.toString(4) shouldBe "undefined"
        }

        "Not indexable" {
            JSUndefined[0] shouldBe JSUndefined
            JSUndefined["abc"] shouldBe JSUndefined
            JSUndefined[JSTrue] shouldBe JSUndefined
            JSUndefined[JSUndefined] shouldBe JSUndefined
            JSUndefined[JSFalse] shouldBe JSUndefined
        }
    }


    "JSNull" - {
        "Conversions" {
            JSNull.toString() shouldBe "null"
            JSNull.toBoolean() shouldBe false
            JSNull.toDouble() shouldBe 0.0
            JSNull.toString(4) shouldBe "null"
        }

        "Not indexable" {
            JSNull[0] shouldBe JSUndefined
            JSNull["abc"] shouldBe JSUndefined
            JSNull[JSTrue] shouldBe JSUndefined
            JSNull[JSFalse] shouldBe JSUndefined
            JSNull[JSUndefined] shouldBe JSUndefined
            JSNull[JSNull] shouldBe JSUndefined

        }
    }

    "JSNumber" - {
        "Conversions" {
            JSNumber(1.0).toDouble() shouldBe 1.0
            JSNumber(1.0).toString() shouldBe "1"
            JSNumber(1.0).toString(4) shouldBe "1"
            JSNumber(-1.0).toString(4) shouldBe "-1"
        }
        "Not indexable" {
            JSNumber(1.0)[0] shouldBe JSUndefined
            JSNumber(1.0)["abc"] shouldBe JSUndefined
            JSNumber(1.0)[JSTrue] shouldBe JSUndefined
            JSNumber(1.0)[JSUndefined] shouldBe JSUndefined
            JSNumber(1.0)[JSFalse] shouldBe JSUndefined
            JSNumber(1.0)[JSNull] shouldBe JSUndefined
        }
    }

    "JSString" - {
        "Conversions" {
            JSString("abc").toString() shouldBe "\"abc\""
            JSString("abc").toString(4) shouldBe "\"abc\""
        }
        "Not string-indexable" {
            JSString("abc")["abc"] shouldBe JSUndefined
            JSString("abc")[JSTrue] shouldBe JSUndefined
        }
        "Char is string" {
            JSString("abc")[1] shouldBe JSString("b")
            JSString("abc")[3] shouldBe JSUndefined
        }
        "Numeric string-indices" {
            JSString("abc")["1.0"] shouldBe JSString("b")
            JSString("abc")["4"] shouldBe JSUndefined
            JSString("abc")["1"] shouldBe JSString("b")
        }
    }

})