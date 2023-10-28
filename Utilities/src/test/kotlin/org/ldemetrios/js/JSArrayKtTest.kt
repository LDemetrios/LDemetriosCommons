package org.ldemetrios.js

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class JSArrayKtTest {
    @Test
    fun simple() {
        val array = JSArray(1.js, 2.js, 3.js)
        assertEquals(array[0], JSNumber(1.0))
        assertEquals(array[1], JSNumber(2.0))
        assertEquals(array[2], JSNumber(3.0))
        assertEquals(array[3], JSUndefined)
        @Suppress("KotlinConstantConditions")
        assertEquals(array[-1], JSUndefined)
        assertEquals(array["-1"], JSUndefined)
        assertEquals(array["2"], JSNumber(3.0))
        array[0] = JSNumber(4.0)
        assertEquals(array[0], JSNumber(4.0))
        assertEquals(array[1], JSNumber(2.0))
        assertEquals(array[2], JSNumber(3.0))
        assertEquals(array.size, 3)
        array[3] = JSString("abc")
        assertEquals(array[3], JSString("abc"))
        assertEquals(array.size, 4)
        array[5] = JSNull
        assertEquals(array[5], JSNull)
        assertEquals(array.size, 6)
        assertEquals(array[4], JSUndefined)
        val another = JSArray(JSTrue, JSFalse)
        assertEquals(JSTrue, another[0])
        assertEquals(JSFalse, another[1])
    }

    @Test
    fun stringifyingSimple() {
        val array = JSArray(1.js, JSTrue, JSNull)
        assertEquals("[1.0, true, null]", array.toString())
        assertEquals("[1.0, true, null]", array.toString(0))
        assertEquals("[1.0,true,null]", array.toString(-1))
    }

    @Test
    fun stringifyingNested() {
        val array = JSArray(1.js, 2.js, 3.js, "null".js, JSUndefined, JSArray(JSTrue, JSFalse), JSNull)
        assertEquals("[1.0, 2.0, 3.0, \"null\", undefined, [true, false], null]", array.toString())
        assertEquals("[1.0, 2.0, 3.0, \"null\", undefined, [true, false], null]", array.toString(0))
        assertEquals("[1.0,2.0,3.0,\"null\",undefined,[true,false],null]", array.toString(-1))
    }
}

