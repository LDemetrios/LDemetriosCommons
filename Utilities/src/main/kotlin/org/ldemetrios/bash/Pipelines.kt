package org.ldemetrios.bash

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/////////////////////////////////////////////////////////////////

// Default usage is, by the way, <String, String>
class PipelineContext<out I, in O>(val input: ReceiveChannel<I>, val output: SendChannel<O>) {
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

fun <I, O> `$`(block: suspend PipelineContext<I, O>.() -> Unit) = line(block).also { println("Line created") }

lateinit var globalScope : CoroutineScope

fun <I, M, O> Conduit<I, M>.pipe(second: Conduit<M, O>): Conduit<I, O> = `$` {
    println("Pipe began")
    val pipe = Channel<M>()
   globalScope.launch { this@pipe(PipelineContext(input, pipe)) }
    second(PipelineContext(pipe, output))
}

infix fun <I, M, O> Conduit<I, M>.`|`(second: Conduit<M, O>): Conduit<I, O> = pipe(second)

suspend fun <I, O> Conduit<I, O>.flow() : List<O> {
    val inp = Channel<I>()
    inp.close()
    val outp = Channel<O>()
    this(PipelineContext(inp, outp))
    return outp.toList()
}

suspend fun <I, O> Conduit<I, O>.`!`() = flow()

fun <I, O> Conduit<I, O>.rinse() = runBlocking {
    globalScope = this
    flow()
}

/////////////////////////////////////////////////////////////////

fun main() {
    (`$`<Any?, String> {
        println("First began")
        echo("1")
        println("First echoed")
        echo("2")
        println("First ended")
    } `|` `$`<String, String> {
        println("Second began")
        for(i in input){
            println(i)
        }
        println("Second ended")
    }).rinse()
}


@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceNumbers() = produce {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}


fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}

