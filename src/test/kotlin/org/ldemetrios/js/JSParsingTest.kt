package org.ldemetrios.js

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class `Parsing JS Test` : FreeSpec({
    "Parsing simple" {
        JSParser.parseValue("true") shouldBe JSTrue
        JSParser.parseValue("false") shouldBe JSFalse
        JSParser.parseValue("null") shouldBe JSNull
        JSParser.parseValue("1") shouldBe 1.js
        JSParser.parseValue("1.0") shouldBe 1.js
        JSParser.parseValue("-1") shouldBe (-1).js
        JSParser.parseValue("-1.0") shouldBe (-1).js
        JSParser.parseValue(""""abc"""") shouldBe "abc".js
    }

    "Parse array" - {
        "Numbers" {
            JSParser.parseValue("[1,2,3]") shouldBe JSArray(1.js, 2.js, 3.js)
        }
        "Mixing" {
            JSParser.parseValue("[1,2,\"a\",\"5\"]") shouldBe JSArray(1.js, 2.js, "a".js, "5".js)
        }
        "Spacing" {
            JSParser.parseValue("  [ 1 ,2,         3.0]") shouldBe JSArray(1.js, 2.js, 3.js)
        }
        "Nested" {
            JSParser.parseValue("[1, 2, [3, 4, [5], 6]]") shouldBe JSArray(
                1.js,
                2.js,
                JSArray(3.js, 4.js, JSArray(5.js), 6.js)
            )
        }
    }

    "Parse object" - {
        "Simple" {
            JSParser.parseValue("""{"a":1,"b":2}""") shouldBe JSObject("a" to 1.js, "b" to 2.js)
        }
        "Spacing" {
            JSParser.parseValue("""  {"a"     :  1 ,    "b":2.0}""") shouldBe
                    JSObject("a" to 1.js, "b" to 2.js)
        }
        "Nested" {
            JSParser.parseValue("""{"a":{"b":{"c":{"d":{"e":{}}}}}}""") shouldBe JSObject(
                "a" to JSObject(
                    "b" to JSObject(
                        "c" to JSObject(
                            "d" to JSObject(
                                "e" to JSObject()
                            )
                        )
                    )
                )
            )
        }
        "Prototype recognized" {
            val obj = JSParser.parseValue(
                """
                    |{
                    |    "__proto__" : {
                    |        "a" : false,
                    |        "b" : {
                    |            "__proto__" : {
                    |                "f" : [
                    |                    4.0,
                    |                    5.0
                    |                ]
                    |            },
                    |            "c" : { "d" : { "e" : {} } }
                    |        }
                    |    },
                    |    "a" : true,
                    |    "bc" : 1.0,
                    |    "de\nf" : [
                    |        2.0,
                    |        3.0
                    |    ]
                    |}
                """.trimMargin()
            )

            obj["a"] shouldBe JSTrue
            obj["bc"] shouldBe 1.js
            obj["de\nf"][0] shouldBe 2.js
            obj["de\nf"][1] shouldBe 3.js
            obj["b"]["c"] shouldBe JSObject("d" to JSObject("e" to JSObject()))
            obj["b"]["f"]["0.0"] shouldBe 4.0.js
        }
    }
})