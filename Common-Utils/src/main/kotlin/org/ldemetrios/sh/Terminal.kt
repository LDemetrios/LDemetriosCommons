@file:Suppress("PackageDirectoryMismatch", "unused")
@file:JvmName("Terminal")

package org.ldemetrios.sh

import org.ldemetrios.utilities.Delayed
import org.ldemetrios.utilities.constants.NEWLINES
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintStream
import java.util.concurrent.atomic.AtomicInteger
import kotlin.text.StringBuilder

private val count = AtomicInteger(0)

private const val CR_CODE = '\r'.code
private const val LF_CODE = '\n'.code
private val NEWLINE_CODES = NEWLINES.map(Char::code)

class PrefixedOutput(val prefix: String, private val underlying: PrintStream) : OutputStream() {
    private var sb = StringBuilder()
    private var rPassed = false
    override fun write(p0: Int) {
        if (rPassed) {
            rPassed = false
            when (p0) {
                LF_CODE -> {
                    underlying.print("$prefix$sb\r\n")
                    sb = StringBuilder()
                }
                CR_CODE -> {
                    underlying.print("$prefix$sb\r")
                    sb = StringBuilder()
                    rPassed = true
                }
                in NEWLINE_CODES -> {
                    underlying.print("$prefix$sb\r$prefix${p0.toChar()}")
                    sb = StringBuilder()
                }

                else -> {
                    underlying.print("$prefix$sb\r")
                    sb = StringBuilder()
                    sb.append(p0.toChar())
                }
            }
        } else {
            when (p0) {
                CR_CODE -> rPassed = true
                in NEWLINE_CODES -> {
                    underlying.print("$prefix$sb${p0.toChar()}")
                    sb = StringBuilder()
                }

                else -> sb.append(p0.toChar())
            }
        }
    }

    override fun close() {
        if (sb.isNotEmpty()) {
            underlying.print("$prefix$sb${System.lineSeparator()}")
        }
        super.close()
    }
}

fun main(){
    println(execute("ls", "-Ali", print = false, doReturn = true).get())
}

@JvmOverloads
fun execute(
    vararg command: String,
    parallel: Boolean = false,
    out: OutputStream? = null,
    print: Boolean = true,
    doReturn: Boolean = false
): Delayed<String> {
    val id = count.getAndIncrement()
    @Suppress("NAME_SHADOWING") val out = out ?: PrefixedOutput("Terminal$id: ", System.out)
    if (parallel) {
        var result: String? = null
        val thread = Thread {
            val res = execute0(id, out, doReturn, print, *command)
            if (res != null) result = res
        }
        thread.start()
        return object : Delayed<String>() {
            override fun compute(): String {
                thread.join()
                return result!!
            }
        }
    } else {
        val res = execute0(id, out,  doReturn, print, *command)
        return object : Delayed<String>() {
            override fun compute(): String {
                return res!!
            }
        }
    }
}

private fun execute0(id: Int, outStr: OutputStream, keep: Boolean, print:Boolean, vararg command: String): String? {
    val output = StringBuilder()
    if(print) println("Run Terminal$id")
    val builder = ProcessBuilder(*command)
    builder.redirectErrorStream(true)
    val process = builder.start()
    val stdout = process.inputStream
    var ch: Int

//    val buffered = BufferedReader(InputStreamReader(stdout))
//    buffered.ready()
    while (stdout.read().also { ch = it } != -1) {
        if (print) outStr.write(ch)
        if (keep) output.append(ch.toChar())
    }
    process.waitFor()
    if(print) println("Terminal$id exited")
    return if (keep) output.toString() else null
}
