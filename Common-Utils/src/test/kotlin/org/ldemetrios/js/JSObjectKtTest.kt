package org.ldemetrios.js

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails


class JSObjectKtTest {
    @Test
    fun simple() {
        val obj = JSObject(
            "abc" to 1.js,
            "def" to JSTrue,
            "ghi" to JSNull,
            "jkl" to JSUndefined,
            "mno" to JSArray(JSTrue, JSFalse),
            "pqr" to JSArray(1.js, JSTrue, JSNull),
            "1.0" to 566.js,
            "2.0" to 239.js,
        )
        assertEquals(1.js, obj["abc"])
        assertEquals(JSTrue, obj["def"])
        assertEquals(JSNull, obj["ghi"])
        assertEquals(JSUndefined, obj["jkl"])
        assertEquals(JSArray(JSTrue, JSFalse), obj["mno"])
        assertEquals(JSArray(1.js, JSTrue, JSNull), obj["pqr"])
        assertEquals(566.js, obj["1.0"])
        assertEquals(566.js, obj[1.0])
        assertEquals(566.js, obj[1])
        assertEquals(JSUndefined, obj["xyz"])
        assertEquals(JSUndefined, obj["2"])
        obj[3] = 4.js
        assertEquals(4.js, obj["3.0"])
        assertEquals(4.js, obj[3])
    }

    @Test
    fun stringifyingSimple() {
        val obj = JSObject(
            "abc" to 1.js,
            "def" to JSTrue,
            "ghi" to JSNull,
        )
        assertEquals("""{"abc" : 1.0, "def" : true, "ghi" : null}""", obj.toString())
        assertEquals("""{"abc" : 1.0, "def" : true, "ghi" : null}""", obj.toString(0))
        assertEquals("""{"abc":1.0,"def":true,"ghi":null}""", obj.toString(-1))
    }

    @Test
    fun stringifyingNested() {
        val obj = JSObject(
            "abc" to JSObject("def" to JSTrue),
            "ghi" to JSArray(JSTrue, JSNull),
            "jkl" to JSArray(JSObject("mno" to JSTrue), JSNull)
        )
        assertEquals("""{"abc" : {"def" : true}, "ghi" : [true, null], "jkl" : [{"mno" : true}, null]}""", obj.toString())
        assertEquals("""{"abc" : {"def" : true}, "ghi" : [true, null], "jkl" : [{"mno" : true}, null]}""", obj.toString(0))
        assertEquals("""{"abc":{"def":true},"ghi":[true,null],"jkl":[{"mno":true},null]}""", obj.toString(-1))
    }

    @Test
    fun stringifyingEmpty() {
        val obj = JSObject()
        assertEquals("{}", obj.toString())
        assertEquals("{}", obj.toString(0))
        assertEquals("{}", obj.toString(-1))
    }

    @Test
    fun stringifyingEmptyNested() {
        val obj = JSObject("abc" to JSObject())
        assertEquals("""{"abc" : {}}""", obj.toString())
        assertEquals("""{"abc" : {}}""", obj.toString(0))
        assertEquals("""{"abc":{}}""", obj.toString(-1))
    }

    @Test
    fun stringifyingEmptyArray() {
        val obj = JSObject("abc" to JSArray())
        assertEquals("""{"abc" : []}""", obj.toString())
        assertEquals("""{"abc" : []}""", obj.toString(0))
        assertEquals("""{"abc":[]}""", obj.toString(-1))
    }

    @Test
    fun stringifyingRecursive() {
        val obj = JSObject(
            "abc" to JSTrue
        )
        obj["def"] = obj
        assertFails { obj.toString() } // TODO Change to proper formatting?
    }

    @Test
    fun protoChain() {
        val obj = JSObject(
            "abc" to JSTrue
        )
        assertEquals(JSTrue, obj["abc"])
        obj.__proto__ = JSObject("ghi" to JSFalse)
        assertEquals(JSFalse, obj["ghi"])
        assertEquals(JSUndefined, obj["1.0"])
        obj.__proto__!!.__proto__ = obj
        assertFails { obj["1.0"] } // TODO Probably do check when assigning prototypes?..
    }
}

