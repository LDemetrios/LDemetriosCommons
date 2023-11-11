package org.ldemetrios.bash

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/////////////////////////////////////////////////////////////////

// Default usage is, by the way, <String, String>
class PipelineContext<out I, in O>(val input: ReceiveChannel<I>, val output: SendChannel<O>, val scope: CoroutineScope) : CoroutineScope by scope {
    suspend fun receive(): I = input.receive()

    suspend fun send(out: O) = output.send(out)
}

private val contextMap = mutableMapOf<PipelineContext<*, String>, String>()

suspend fun <I> PipelineContext<I, String>.echo(message: String = "") {
    send((contextMap.replace(this, "") ?: "") + message)
}

fun <I> PipelineContext<I, String>.echo_n(message: String) {
    contextMap.compute(this) { _, value -> (value ?: "") + message }
}

suspend fun <O> PipelineContext<String, O>.read() = receive().split(" \t").filter { it.isNotBlank() }

/////////////////////////////////////////////////////////////////

fun interface Conduit<I, O> {
    suspend operator fun invoke(pipelineContext: PipelineContext<I, O>)
}

fun <I, O> line(block: suspend PipelineContext<I, O>.() -> Unit): Conduit<I, O> = Conduit {
    it.block()
    it.output.close()
}

fun <O> `$`(block: suspend PipelineContext<Nothing, O>.() -> Unit) = line(block)

fun <I, M, O> Conduit<I, M>.pipe(second: Conduit<M, O>): Conduit<I, O> = line {
    val pipe = Channel<M>(3)
    val job = launch { this@pipe(PipelineContext(input, pipe, this)) }
    second(PipelineContext(pipe, output, this.scope))
    job.cancel()
}

fun <I, M, O> Conduit<I, M>.pipe(block: suspend PipelineContext<M, O>.() -> Unit): Conduit<I, O> = pipe(line(block))
fun <I, M> Conduit<I, M>.pipe(rinser: Rinser): List<M> = rinse()

infix fun <I, M, O> Conduit<I, M>.`|`(second: Conduit<M, O>): Conduit<I, O> = pipe(second)

infix fun <I, M, O> Conduit<I, M>.`|`(block: suspend PipelineContext<M, O>.() -> Unit): Conduit<I, O> = pipe(block)
infix fun <I, M> Conduit<I, M>.`|`(rinser: Rinser): List<M> = pipe(rinser)

suspend fun <I, O> Conduit<I, O>.flow(scope: CoroutineScope): List<O> {
    val inp = Channel<I>()
    inp.close()
    val outp = Channel<O>()
    scope.launch {
        this@flow(PipelineContext(inp, outp, this))
    }
    return outp.toList()
}

val <I, O> Conduit<I, O>.`!` get() = rinse()

object Rinser

val `!` = Rinser

fun <I, O> Conduit<I, O>.rinse() = runBlocking {
    flow(this)
}

/////////////////////////////////////////////////////////////////

fun main() {
    val x = `$` {
        for (i in 0 until Int.MAX_VALUE) {
            send(i)
        }
    } `|` {
        var lim = 0
        for (i in input) {
            send(i * i)
            if (lim++ == 5) break
        }
    } `|` `!`
    println(x)
}


@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceNumbers() = produce {
    var x = 14
    while (true) send(x++) // infinite stream of integers starting from 1
}


fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}

