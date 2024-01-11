package org.ldemetrios.sh

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/////////////////////////////////////////////////////////////////

// Default usage is, by the way, <String, String>
class PipelineContext<out I, in O>(val input: ReceiveChannel<I>, val output: SendChannel<O>, val scope: CoroutineScope) : CoroutineScope by scope {
    suspend fun receive(): I = input.receive()

    suspend fun send(out: O) = output.send(out)
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

operator fun <I, M, O> Conduit<I, M>.div(second: Conduit<M, O>): Conduit<I, O> = pipe(second)

operator fun <I, M, O> Conduit<I, M>.div(block: suspend PipelineContext<M, O>.() -> Unit): Conduit<I, O> = pipe(block)
operator fun <I, M> Conduit<I, M>.div(rinser: Rinser): List<M> = pipe(rinser)


suspend fun <I, O> Conduit<I, O>.flow(scope: CoroutineScope): List<O> {
    val inp = Channel<I>()
    inp.close()
    val outp = Channel<O>()
    scope.launch {
        this@flow(PipelineContext(inp, outp, this))
    }
    return outp.toList()
}

object Rinser

val `!` = Rinser

fun <I, O> Conduit<I, O>.rinse() = runBlocking {
    flow(this)
}


data class Cat<I, O>(val context: PipelineContext<I, O>);
val <I, O> PipelineContext<I, O>.cat get() = Cat(this)

suspend operator fun <I, O> Conduit<Nothing, O>.div(cat: Cat<I, O>) {
    this / {
        for (e in input) cat.context.send(e)
        send(null)
    } / `!`
}

operator fun <T, R> T.rem(func:(T) -> R) = func(this)
suspend operator fun <T, R> T.rem(func:suspend (T) -> R) = func(this)
