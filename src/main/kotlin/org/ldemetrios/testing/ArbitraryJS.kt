package org.ldemetrios.testing

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import org.ldemetrios.js.*

private val c = Codepoint.printableAscii()
    .withEdgecases("\n\r\t\b".map { Codepoint(it.code) })

fun Arb.Companion.jsNumber() = Arb.choice(
    Arb.double().map { it.js },
    Arb.float().map { it.js },
    Arb.int().map { it.js }
)


fun Arb.Companion.jsPrimitive(): Arb<JSPrimitive> = jsNumber().withEdgecases(JSNull, JSUndefined, JSTrue, JSFalse)

fun Arb.Companion.jsString(limit: Int) = Arb.string(0, limit, c).map { it.js }


