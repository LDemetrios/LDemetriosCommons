package org.ldemetrios.js

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.ldemetrios.testing.shouldFormatTo


class `JSObject Test` : FreeSpec({
    "Get/Set manipulations" - {
        fun gen() = JSObject(
            "abc" to 1.js,
            "def" to JSTrue,
            "ghi" to JSNull,
            "jkl" to JSUndefined,
            "mno" to JSArray(JSTrue, JSFalse),
            "pqr" to JSArray(1.js, JSTrue, JSNull),
            "1.0" to 566.js,
            "2.0" to 239.js,
        )

        val obj = gen()

        "Array access" {
            obj["abc"] shouldBe 1.js
            obj["def"] shouldBe JSTrue
            obj["ghi"] shouldBe JSNull
            obj["jkl"] shouldBe JSUndefined
            obj["mno"] shouldBe JSArray(JSTrue, JSFalse)
            obj["pqr"] shouldBe JSArray(1.js, JSTrue, JSNull)
            obj["1.0"] shouldBe 566.js
        }

        "Missing key" {
            obj["xyz"] shouldBe JSUndefined
            obj["2"] shouldBe JSUndefined
        }

        "Numeric index access" {
            obj[1.0] shouldBe 566.js
            obj[1] shouldBe 566.js
        }

        "Modifying" {
            val obj = gen()
            obj[3] = 4.js
            obj["3.0"] shouldBe 4.js
            obj[3] shouldBe 4.js
        }
    }

    "Basic stringification" - {
        val obj = JSObject(
            "abc" to 1.js,
            "def" to JSTrue,
            "ghi" to JSNull,
        )

        "Default" {
            obj shouldFormatTo """{"abc" : 1.0, "def" : true, "ghi" : null}"""
        }
        "In line" {
            obj.toString(0) shouldFormatTo """{"abc" : 1.0, "def" : true, "ghi" : null}"""
        }
        "Dense" {
            obj.toString(-1) shouldFormatTo """{"abc":1.0,"def":true,"ghi":null}"""
        }
    }

    "Stringifying nested" - {
        val obj = JSObject(
            "abc" to JSObject("def" to JSTrue),
            "ghi" to JSArray(JSTrue, JSNull),
            "jkl" to JSArray(JSObject("mno" to JSTrue), JSNull)
        )
        "Default" {
            obj shouldFormatTo """{"abc" : {"def" : true}, "ghi" : [true, null], "jkl" : [{"mno" : true}, null]}"""
        }
        "In line" {
            obj.toString(0) shouldFormatTo """{"abc" : {"def" : true}, "ghi" : [true, null], "jkl" : [{"mno" : true}, null]}"""
        }
        "Dense" {
            obj.toString(-1) shouldFormatTo """{"abc":{"def":true},"ghi":[true,null],"jkl":[{"mno":true},null]}"""
        }
    }

    "Stringifying empty" {
        val obj = JSObject()
        obj.toString() shouldBe "{}"
        obj.toString(0) shouldBe "{}"
        obj.toString(-1) shouldBe "{}"
    }

    "Stringifying empty nested" {
        val obj = JSObject("abc" to JSObject())
        obj.toString() shouldBe """{"abc" : {}}"""
        obj.toString(0) shouldBe """{"abc" : {}}"""
        obj.toString(-1) shouldBe """{"abc":{}}"""
    }

    "Stringifying recursive" {
        val obj = JSObject(
            "abc" to JSTrue
        )
        obj["def"] = obj
        shouldThrow<StackOverflowError> { obj.toString() } // TODO Change to proper formatting?
    }

    "Prototype chain" {
        val obj = JSObject(
            "abc" to JSTrue
        )
        obj["abc"] shouldBe JSTrue
        obj.__proto__ = JSObject("ghi" to JSFalse)
        obj["ghi"] shouldBe JSFalse
        obj["1.0"] shouldBe JSUndefined
        obj.__proto__!!.__proto__ = obj
        shouldThrow<StackOverflowError> { obj["1.0"] } // TODO Probably do check when assigning prototypes?..
    }
})

