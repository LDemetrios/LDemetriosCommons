package org.ldemetrios.js

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class JSStuffKtTest {
    @Test
    fun asJS() {
    }

    @Test
    fun isBamboo() {
        assertEquals(true, JSTrue.isBamboo())
        assertEquals(true, JSFalse.isBamboo())
        assertEquals(true, JSUndefined.isBamboo())
        assertEquals(true, JSNull.isBamboo())
        assertEquals(true, JSNumber(1.0).isBamboo())
        assertEquals(true, JSString("abc").isBamboo())
        assertEquals(true, JSArray(JSTrue).isBamboo())

        assertEquals(true, JSArray(JSNumber(1.0)).isBamboo())
        assertEquals(true, JSArray(JSString("abc")).isBamboo())
        assertEquals(true, JSArray(JSArray(JSTrue) as JSStuff).isBamboo())
        assertEquals(true, JSArray(JSArray(JSFalse) as JSStuff).isBamboo())
        assertEquals(true, JSArray(JSArray(JSUndefined) as JSStuff).isBamboo())
        assertEquals(true, JSArray(JSArray(JSNull) as JSStuff).isBamboo())
        assertEquals(true, JSArray(JSArray(JSNumber(1.0)) as JSStuff).isBamboo())
        assertEquals(false, JSArray(JSArray(), JSTrue).isBamboo())
        assertEquals(false, JSArray(JSArray(JSArray(JSArray(JSTrue, JSFalse) as JSStuff) as JSStuff) as JSStuff).isBamboo())
        assertEquals(
            false, JSObject(
                "abc" to JSArray(
                    JSObject(
                        "def" to JSArray(JSArray(JSTrue, JSFalse) as JSStuff)
                    ) as JSStuff
                )
            ).isBamboo()
        )
        assertEquals(true, JSObject("a" to JSObject("b" to JSObject("c" to JSObject()))).isBamboo())
    }

    @Test
    fun jsBoolean() {
        assertEquals(true, JSTrue.toBoolean())
        assertEquals("true", JSTrue.toString())
        assertEquals(1.0, JSTrue.toDouble())
        assertEquals(false, JSFalse.toBoolean())
        assertEquals("false", JSFalse.toString())
        assertEquals(0.0, JSFalse.toDouble())
        assertEquals(true, JSBoolean.of(true).toBoolean())
        assertEquals(false, JSBoolean.of(false).toBoolean())
        assertEquals("true", JSBoolean.of(true).toString())
        assertEquals("false", JSBoolean.of(false).toString())
        assertEquals(1.0, JSBoolean.of(true).toDouble())
        assertEquals(0.0, JSBoolean.of(false).toDouble())
        assertEquals("true", JSBoolean.of(true).toString(4))
        assertEquals("false", JSBoolean.of(false).toString(4))
        assertEquals(JSUndefined, JSTrue[0])
        assertEquals(JSUndefined, JSFalse[0])
        assertEquals(JSUndefined, JSTrue["abc"])
        assertEquals(JSUndefined, JSFalse["abc"])
        assertEquals(JSUndefined, JSTrue[JSTrue])
        assertEquals(JSUndefined, JSFalse[JSUndefined])
    }

    @Test
    fun jsUndefined() {
        assertEquals("undefined", JSUndefined.toString())
        assertEquals(false, JSUndefined.toBoolean())
        assertEquals(0.0, JSUndefined.toDouble())
        assertEquals("undefined", JSUndefined.toString(4))
        assertEquals(JSUndefined, JSUndefined[0])
        assertEquals(JSUndefined, JSUndefined["abc"])
        assertEquals(JSUndefined, JSUndefined[JSTrue])
        assertEquals(JSUndefined, JSUndefined[JSUndefined])
        assertEquals(JSUndefined, JSUndefined[JSFalse])
    }

    @Test
    fun jsNull() {
        assertEquals("null", JSNull.toString())
        assertEquals(false, JSNull.toBoolean())
        assertEquals(0.0, JSNull.toDouble())
        assertEquals("null", JSNull.toString(4))
        assertEquals(JSUndefined, JSNull[0])
        assertEquals(JSUndefined, JSNull["abc"])
        assertEquals(JSUndefined, JSNull[JSTrue])
        assertEquals(JSUndefined, JSNull[JSFalse])
        assertEquals(JSUndefined, JSNull[JSBoolean.of(true)])
        assertEquals(JSUndefined, JSNull[JSBoolean.of(false)])
        assertEquals(JSUndefined, JSNull[JSUndefined])
        assertEquals(JSUndefined, JSNull[JSNull])
    }

    @Test
    fun jsNumber() {
        assertEquals(1.0, JSNumber(1.0).toDouble())
        assertEquals("1", JSNumber(1.0).toString())
        assertEquals("1", JSNumber(1.0).toString(4))
        assertEquals("-1", JSNumber(-1.0).toString(4))
        assertEquals(JSUndefined, JSNumber(1.0)[0])
        assertEquals(JSUndefined, JSNumber(1.0)["abc"])
        assertEquals(JSUndefined, JSNumber(1.0)[JSTrue])
        assertEquals(JSUndefined, JSNumber(1.0)[JSUndefined])
        assertEquals(JSUndefined, JSNumber(1.0)[JSFalse])
        assertEquals(JSUndefined, JSNumber(1.0)[JSNull])
    }


    @Test
    fun jsString() {
        assertEquals("\"abc\"", JSString("abc").toString())
        assertEquals("\"abc\"", JSString("abc").toString(4))
        assertEquals(JSString("b"), JSString("abc")[1])
        assertEquals(JSUndefined, JSString("abc")[3])
        assertEquals(JSUndefined, JSString("abc")["abc"])
        assertEquals(JSUndefined, JSString("abc")[JSTrue])
        assertEquals(JSString("b"), JSString("abc")["1.0"])
        assertEquals(JSUndefined, JSString("abc")["4"])
        assertEquals(JSString("b"), JSString("abc")["1"])
    }
}