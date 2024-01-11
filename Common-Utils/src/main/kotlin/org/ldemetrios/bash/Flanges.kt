package org.ldemetrios.bash

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.toList
import org.ldemetrios.utilities.Either

fun interface Rinsable<in I, out O, out E, out Ex : Throwable> {
    fun rinseWith(list: List<I>): Triple<List<O>, List<E>, Ex?>
}

fun <O, E, Ex : Throwable> Rinsable<Nothing, O, E, Ex>.rinse() = rinseWith(listOf())

class PipelineContext<out I, in O, in E>(
    val input: ReceiveChannel<I>,
    val output: SendChannel<O>,
    val error: SendChannel<E>,
    val scope: CoroutineScope
) : CoroutineScope by scope {
    var name: String = ""

    fun name(name: String) {
        this.name = name
//        println("$name: $name started")
    }

    fun finished() {
//        println("$name: $name finished")
    }

    suspend fun receive(): I = log("$name: receive") { input.receive() }

    suspend fun send(out: O) = log("$name: send $out") { output.send(out) }

    suspend fun sendError(err: E) = error.send(err)

    suspend fun <Ex : Throwable> subshell(subshell: Rinsable<Nothing, O, E, Ex>) {
        val (out, err, ex) = subshell.rinse()

        for (line in err) {
            error.send(line)
        }
        for (line in out) {
            send(line)
        }

        if (ex != null) throw ex
    }

    suspend fun <Ex : Throwable> eval(subshell: Rinsable<Nothing, O, E, Ex>): List<@UnsafeVariance O> {
        val (out, err, ex) = subshell.rinse()

        for (line in err) {
            error.send(line)
        }

        if (ex != null) throw ex
        return out
    }

    suspend fun <Ex : Throwable> evalSafely(subshell: Rinsable<Nothing, O, E, Ex>): Either<List<@UnsafeVariance O>, Ex> {
        val (out, err, ex) = subshell.rinse()

        for (line in err) {
            error.send(line)
        }

        if (ex != null) return Either.Right(ex)
        return Either.Left(out)
    }
}

interface Flange<in I, out O, out E> {
    suspend operator fun invoke(pipelineContext: PipelineContext<I, O, E>): Unit
}

interface UnsafeFlange<in I, out O, out E, out Ex : Throwable> : Flange<I, O, E>, Rinsable<I, O, E, Ex> {
    val clazz: Class<@UnsafeVariance Ex>

    override suspend operator fun invoke(pipelineContext: PipelineContext<I, O, E>): Unit

    override fun rinseWith(list: List<I>): Triple<List<O>, List<E>, Ex?> {
        return Aqueduct<I, O, _, Ex>(clazz, listOf(this)).rinseWith(list)
    }
}

private class NeverException : Throwable()

interface SafeFlange<in I, out O, out E> : Flange<I, O, E>, Rinsable<I, O, E, Nothing> {
    override suspend operator fun invoke(pipelineContext: PipelineContext<I, O, E>): Unit

    override fun rinseWith(list: List<I>): Triple<List<O>, List<E>, Nothing?> {
        return Aqueduct<I, O, _, Nothing>(NeverException::class.java, listOf(this)).rinseWith(list)
    }
}


inline fun <I, O, E, reified Ex : Throwable> UnsafeFlange(crossinline func: suspend PipelineContext<I, O, E>.() -> Unit) =
    object : UnsafeFlange<I, O, E, Ex> {
        override val clazz = Ex::class.java

        override suspend operator fun invoke(pipelineContext: PipelineContext<I, O, E>) = pipelineContext.func()
    }


inline fun <I, O, E> SafeFlange(crossinline func: suspend PipelineContext<I, O, E>.() -> Unit) =
    object : SafeFlange<I, O, E> {
        override suspend operator fun invoke(pipelineContext: PipelineContext<I, O, E>) = pipelineContext.func()
    }


suspend fun <T> log(str: String, what: suspend () -> T): T {
//    println(str)
    val e = what()
//    println("done $str" + if (e == Unit) "" else " => $e")
    return e
}

inline fun <IO, E> barrier() = SafeFlange<IO, IO, E> {
    name("barrier")
    val input = log("it.input.toList()") { input.toList() }
    for (el in input) {
        log("it.send($el)") { send(el) }
    }
    finished()
    null
}

fun <I, O, E, Except : Throwable> UnsafeFlange<I, O, E, Except>.named(str: String) =
    object : UnsafeFlange<I, O, E, Except> by this {
        override fun toString(): String = str
    }


fun <O, E, Ex : Throwable> shell(aqueduct: Rinsable<Nothing, O, E, Ex>): List<O> {
    val result = aqueduct.rinse()

    val (out, err, ex) = result

    if (ex != null) throw ex
    for (line in err) {
        System.err.println(line)
    }

    return out
}

fun <O, E, Ex : Throwable> safeShell(aqueduct: Rinsable<Nothing, O, E, Ex>): Either<List<@UnsafeVariance O>, Ex> {
    val (out, err, ex) = aqueduct.rinse()

    for (line in err) {
        System.err.println(line)
    }

    if (ex != null) return Either.Right(ex)
    return Either.Left(out)
}