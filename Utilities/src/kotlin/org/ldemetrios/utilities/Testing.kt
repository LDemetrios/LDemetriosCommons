@file:Suppress("PackageDirectoryMismatch", "unused")
@file:JvmName("Testing")

package org.ldemetrios.utils.testing

import  org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable

fun Assertions_assertAll(vararg assertions: () -> Unit) = Assertions.assertAll(assertions.map { assertion -> Executable { assertion() } })
