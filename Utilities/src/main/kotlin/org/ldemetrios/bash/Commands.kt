package org.ldemetrios.bash

import kotlinx.coroutines.launch
import org.ldemetrios.utilities.constants.NEWLINES
import java.io.OutputStream
import java.io.PrintStream

fun <T> head(n: Int): Conduit<T, T> = line {
    var lim = 0;
    for (e in input) {
        if (lim++ >= n) break
        output.send(e)
    }
}

private const val CR_CODE = '\r'.code
private const val LF_CODE = '\n'.code
private val NEWLINE_CODES = NEWLINES.map(Char::code)

class SendingOutput(private val sender: suspend (String) -> Unit) {
    private var sb = StringBuilder()
    private var rPassed = false
    suspend fun write(p0: Int) {
        if (rPassed) {
            val line = sb.toString()
            sb = StringBuilder()
            rPassed = false
            when (p0) {
                LF_CODE -> sender(line)
                CR_CODE -> {
                    sender(line)
                    rPassed = true
                }

                in NEWLINE_CODES -> {
                    sender(line)
                    sender("")
                }

                else -> {
                    sender(line)
                    sb.append(p0.toChar())
                }
            }
        } else {
            when (p0) {
                CR_CODE -> rPassed = true
                in NEWLINE_CODES -> {
                    sender(sb.toString())
                    sb = StringBuilder()
                }

                else -> sb.append(p0.toChar())
            }
        }
    }

    suspend fun close() {
        if (sb.isNotEmpty()) {
            sender(sb.toString())
        }
    }
}

fun bash(vararg command: String): Conduit<String, String>  = line{
    val output = SendingOutput(this::send)
    val builder = ProcessBuilder(*command)
    builder.redirectErrorStream(true)
    val process = builder.start()

    for (line in input) {
        process.outputStream.write(line.toByteArray())
        process.outputStream.write(System.lineSeparator().toByteArray())
    }
    process.outputStream.close()

    val stdout = process.inputStream
    var ch: Int
    while (stdout.read().also { ch = it } != -1) {
        output.write(ch)
    }
    process.waitFor()
}
//
//fun main() {
//    println(bash("ls", "-Ali") `|` bash("wc", "-l") `|` `!`)
//}