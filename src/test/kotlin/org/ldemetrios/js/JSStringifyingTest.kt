package org.ldemetrios.js

import org.junit.jupiter.api.Test
import org.ldemetrios.utilities.cast
import kotlin.test.assertEquals
import kotlin.test.assertFails

class JSStringifyingTest {
    @Test
    fun simple() {
        val obj = JSObject()
        obj["a"] = "b".js
        assertEquals("{\"a\":\"b\"}", obj.toString(-1))
        assertEquals("{\"a\" : \"b\"}", obj.toString())
        assertEquals("""{ "a" : "b" }""".trimMargin(), obj.toString(2))
        obj["c"] = 2.js
        assertEquals("{\"a\":\"b\",\"c\":2.0}", obj.toString(-1))
        assertEquals("{\"a\" : \"b\", \"c\" : 2.0}", obj.toString())
        assertEquals(
            """{
            |  "a" : "b",
            |  "c" : 2.0
            |}""".trimMargin(), obj.toString(2)
        )
        assertEquals(
            """{
            |    "a" : "b",
            |    "c" : 2.0
            |}""".trimMargin(), obj.toString(4)
        )
        obj["d"] = JSObject("e" to JSObject())
        assertEquals(
            """{
                |    "a" : "b",
                |    "c" : 2.0,
                |    "d" : { "e" : {} }
                |}""".trimMargin(), obj.toString(4)
        )
        obj["f"] = JSArray(1.js, 2.js, 3.js)
        assertEquals(
            """{
                |    "a" : "b",
                |    "c" : 2.0,
                |    "d" : { "e" : {} },
                |    "f" : [
                |        1.0,
                |        2.0,
                |        3.0
                |    ]
                |}""".trimMargin(), obj.toString(4)
        )
        fun arr(of: JSStuff) = JSArray(of) as JSStuff
        fun obj(of: JSStuff) = JSObject("a" to of) as JSStuff
        assertEquals("[[[[[[[[[]]]]]]]]]", arr(arr(arr(arr(arr(arr(arr(arr(JSArray())))))))).toString(0))
        assertEquals("[[[[[[[[[]]]]]]]]]", arr(arr(arr(arr(arr(arr(arr(arr(JSArray())))))))).toString(-1))
        assertEquals("[ [ [ [ [ [ [ [ [] ] ] ] ] ] ] ] ]", arr(arr(arr(arr(arr(arr(arr(arr(JSArray())))))))).toString(4))
        assertEquals("""{"a" : {"a" : {"a" : {"a" : {"a" : {"a" : {"a" : {"a" : {}}}}}}}}}""", obj(obj(obj(obj(obj(obj(obj(obj(JSObject())))))))).toString(0))
        assertEquals("""{"a":{"a":{"a":{"a":{"a":{"a":{"a":{"a":{}}}}}}}}}""", obj(obj(obj(obj(obj(obj(obj(obj(JSObject())))))))).toString(-1))
        assertEquals("""{ "a" : { "a" : { "a" : { "a" : { "a" : { "a" : { "a" : { "a" : {} } } } } } } } }""", obj(obj(obj(obj(obj(obj(obj(obj(JSObject())))))))).toString(4))
        assertEquals(
            """[
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
                        |]""".trimMargin(), arr(arr(arr(arr(arr(arr(arr(arr(arr(arr(arr(JSArray(1.js, 2.js)))))))))))).toString(2)
        )
    }

    @Test
    fun proto() {
        val obj = JSObject("a" to JSTrue, "bc" to 1.js, "de\nf" to JSArray(2.js, 3.js))
        obj.__proto__ = JSObject("a" to JSObject("b" to JSObject("c" to JSObject("d" to JSObject("e" to JSObject())))))
        obj.__proto__["a"]["b"].cast<JSObject>().__proto__ = JSObject("f" to JSArray(4.js, 5.js))
        assertEquals(
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
            """.trimMargin(), obj.toString(4)
        )
    }

    @Test
    fun parsingSimple() {
        assertEquals(JSTrue, JSParser.parseValue("true"))
        assertEquals(JSFalse, JSParser.parseValue("false"))
        assertEquals(JSNull, JSParser.parseValue("null"))
        assertEquals(1.js, JSParser.parseValue("1"))
        assertEquals(1.js, JSParser.parseValue("1.0"))
        assertEquals((-1).js, JSParser.parseValue("-1"))
        assertEquals((-1).js, JSParser.parseValue("-1.0"))
        assertEquals("abc".js, JSParser.parseValue(""""abc""""))
    }

    @Test
    fun parseArray() {
        assertEquals(JSArray(1.js, 2.js, 3.js), JSParser.parseValue("[1,2,3]"))
        assertEquals(JSArray(1.js, 2.js, 3.js), JSParser.parseValue("  [ 1 ,2,         3.0]"))
    }

    @Test
    fun parseObject() {
        assertEquals(JSObject("a" to 1.js, "b" to 2.js), JSParser.parseValue("""{"a":1,"b":2}"""))
        assertEquals(JSObject("a" to 1.js, "b" to 2.js), JSParser.parseValue("""  {"a"     :  1 ,    "b":2.0}"""))
    }

@Test
    fun parseFails() {
        assertFails { JSParser.parseValue("""{"a":1,"b:2}""") }
        // TODO add more tests
    }

    @Test
    fun parseComplex() {
        assertEquals(
            JSObject(
                "a" to JSObject(
                    "b" to JSObject(
                        "c" to JSObject(
                            "d" to JSObject(
                                "e" to JSObject()
                            )
                        )
                    )
                )
            ), JSParser.parseValue("""{"a":{"b":{"c":{"d":{"e":{}}}}}}""")
        )
        val obj =
            JSParser.parseValue(
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
        assertEquals(JSTrue, obj["a"])
        assertEquals(1.js, obj["bc"])
        assertEquals(2.js, obj["de\nf"][0])
        assertEquals(3.js, obj["de\nf"][1])
        assertEquals(JSObject("d" to JSObject("e" to JSObject())), obj["b"]["c"])
        assertEquals(4.0.js, obj["b"]["f"]["0.0"])
    }
}