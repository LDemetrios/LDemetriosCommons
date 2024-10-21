package org.ldemetrios.testing

import io.kotest.matchers.shouldBe
import org.ldemetrios.functional.get
import org.ldemetrios.utilities.constants.NEWLINE_PATTERN
//import org.ldemetrios.utilities.functionToString
import org.ldemetrios.utilities.isStatic
import java.io.IOException
import java.nio.file.Files
import kotlin.io.path.absolutePathString


infix fun String.shouldBeEqualsIgnoringLineSeps(x: String) =
    this.replace(NEWLINE_PATTERN, "\n") shouldBe x.replace(NEWLINE_PATTERN, "\n")


infix fun Any?.shouldFormatTo(s: String) = this.toString() shouldBeEqualsIgnoringLineSeps s
