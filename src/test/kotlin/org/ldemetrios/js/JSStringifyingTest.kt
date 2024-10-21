package org.ldemetrios.js

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.ldemetrios.testing.shouldFormatTo
import org.ldemetrios.utilities.cast

class `JS Stringify test` : FreeSpec({
    "Indentation" {
        val obj = JSObject()
        obj["a"] = "b".js
        obj["c"] = 2.js
        obj.toString() shouldBe "{\"a\" : \"b\", \"c\" : 2.0}"
        obj.toString(2) shouldFormatTo
                """
                    |{
                    |  "a" : "b",
                    |  "c" : 2.0
                    |}
                """.trimMargin()
        obj.toString(4) shouldFormatTo
                """
                    |{
                    |    "a" : "b",
                    |    "c" : 2.0
                    |}
                """.trimMargin()
        // TODO We certainly could benefit from having generic formatter, independent of what we are formatting
    }

    fun arr(of: JSStuff) = JSArray(of) as JSStuff
    fun obj(of: JSStuff) = JSObject("a" to of) as JSStuff

    "Bamboo" - {
        "Regular" {
            val obj = JSObject()
            obj["a"] = "b".js
            obj.toString(2) shouldFormatTo """{ "a" : "b" }""".trimMargin()
        }
        "Inside" {
            val obj = JSObject()
            obj["a"] = "b".js
            obj["c"] = 2.js
            obj["d"] = JSObject("e" to JSObject())
            obj.toString(4) shouldFormatTo
                    """
                        |{
                        |    "a" : "b",
                        |    "c" : 2.0,
                        |    "d" : { "e" : {} }
                        |}
                    """.trimMargin()
        }
        "Very nested arrays" {
            val x = arr(arr(arr(arr(arr(arr(arr(arr(JSArray()))))))))
            x.toString(0) shouldFormatTo "[[[[[[[[[]]]]]]]]]"
            x.toString(4) shouldFormatTo "[ [ [ [ [ [ [ [ [] ] ] ] ] ] ] ] ]"
        }
        "Very nested objects" {
            val x = obj(obj(obj(obj(obj(obj(obj(obj(JSObject()))))))))

            x.toString(-1) shouldFormatTo """{"a":{"a":{"a":{"a":{"a":{"a":{"a":{"a":{}}}}}}}}}"""
            x.toString(4) shouldFormatTo """{ "a" : { "a" : { "a" : { "a" : { "a" : { "a" : { "a" : { "a" : {} } } } } } } } }"""
        }
        "Not bambooing spreads" {
            val x = arr(arr(arr(arr(arr(arr(arr(arr(arr(arr(arr(JSArray(1.js, 2.js))))))))))))
            x.toString(2) shouldFormatTo """
                |[
                |  [
                |    [
                |      [
                |        [
                |          [
                |            [
                |              [
                |                [
                |                  [
                |                    [
                |                      [
                |                        1.0,
                |                        2.0
                |                      ]
                |                    ]
                |                  ]
                |                ]
                |              ]
                |            ]
                |          ]
                |        ]
                |      ]
                |    ]
                |  ]
                |]
            """.trimMargin()
        }
    }

    "Proto is a field" {
        val obj = JSObject("a" to JSTrue, "bc" to 1.js, "de\nf" to JSArray(2.js, 3.js))
        obj.__proto__ = JSObject("a" to JSObject("b" to JSObject("c" to JSObject("d" to JSObject("e" to JSObject())))))
        obj.__proto__["a"]["b"].cast<JSObject>().__proto__ = JSObject("f" to JSArray(4.js, 5.js))
        obj.toString(4) shouldFormatTo
                """
                    |{
                    |    "__proto__" : {
                    |        "a" : {
                    |            "b" : {
                    |                "__proto__" : {
                    |                    "f" : [
                    |                        4.0,
                    |                        5.0
                    |                    ]
                    |                },
                    |                "c" : { "d" : { "e" : {} } }
                    |            }
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
    }
})