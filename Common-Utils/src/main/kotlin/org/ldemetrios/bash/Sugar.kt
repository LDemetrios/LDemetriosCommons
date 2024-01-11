package org.ldemetrios.bash.sugar

import kotlinx.coroutines.CancellationException
import org.ldemetrios.bash.*
import org.ldemetrios.sh.rem
import org.ldemetrios.utilities.Either


// TODO Checking exceptions

//////////////////////////////////////////////////////////// Flange generators

// I, O, E, Ex
inline fun <I, O, E, reified Ex : Throwable> unsafeDirty(crossinline func: suspend PipelineContext<I, O, E>.() -> Unit) =
    UnsafeFlange<I, O, E, Ex>(func)

//// Nothing, O, E, Ex
//inline fun <O, E, reified Ex : Throwable> SUnsafeDirty(crossinline func: suspend PipelineContext<Nothing, O, E>.() -> Unit) =
//    UnsafeFlange<Nothing, O, E, Ex>(func)

// I, O, Nothing, Ex
inline fun <I, O, reified Ex : Throwable> unsafe(crossinline func: suspend PipelineContext<I, O, Nothing>.() -> Unit) =
    UnsafeFlange<I, O, Nothing, Ex>(func)

//// Nothing, O, Nothing, Ex
//inline fun <O, reified Ex : Throwable> SUnsafe(crossinline func: suspend PipelineContext<Nothing, O, Nothing>.() -> Unit) =
//    UnsafeFlange<Nothing, O, Nothing, Ex>(func)

// I, O, E, Nothing
fun <I, O, E> dirty(func: suspend PipelineContext<I, O, E>.() -> Unit) = SafeFlange<I, O, E>(func)

//// Nothing, O, E, Nothing
//fun <O, E> SDirty(func: suspend PipelineContext<Nothing, O, E>.() -> Unit) = SafeFlange<Nothing, O, E>(func)

// I, O, Nothing, Nothing
// No syntax -- just lambda

//// Nothing, O, Nothing, Nothing
//fun <O> S(func: suspend PipelineContext<Nothing, O, Nothing>.() -> Unit) = SafeFlange<Nothing, O, Nothing>(func)

object Dollar {
    // Nothing, O, E, Ex
    inline fun <O, E, reified Ex : Throwable> unsafeDirty(crossinline func: suspend PipelineContext<Nothing, O, E>.() -> Unit) =
        UnsafeFlange<Nothing, O, E, Ex>(func)

    // Nothing, O, Nothing, Ex
    inline fun <O, reified Ex : Throwable> unsafe(crossinline func: suspend PipelineContext<Nothing, O, Nothing>.() -> Unit) =
        UnsafeFlange<Nothing, O, Nothing, Ex>(func)

    // Nothing, O, E, Nothing
    fun <O, E> dirty(func: suspend PipelineContext<Nothing, O, E>.() -> Unit) = SafeFlange<Nothing, O, E>(func)

    // Nothing, O, Nothing, Nothing
    operator fun <O> invoke(func: suspend PipelineContext<Nothing, O, Nothing>.() -> Unit) =
        SafeFlange<Nothing, O, Nothing>(func)
}

typealias S = Dollar
typealias `$` = Dollar

//////////////////////////////////////////////////////////// Piping

operator fun <I, M, O, E> (SafeFlange<I, M, E>).div(b: SafeFlange<M, O, E>) = pipe<I, M, O, E>(this, b)
inline operator fun <I, M, O, E, reified Ex : Throwable> (SafeFlange<I, M, E>).div(b: UnsafeFlange<M, O, E, Ex>) =
    pipe<I, M, O, E, Ex>(this, b)

inline operator fun <I, M, O, E, Ex : Throwable> (SafeFlange<I, M, E>).div(b: Aqueduct<M, O, E, Ex>) =
    pipe<I, M, O, E, Ex>(this, b)

inline operator fun <I, M, O, E, reified Ex : Throwable> (UnsafeFlange<I, M, E, Ex>).div(b: SafeFlange<M, O, E>) =
    pipe<I, M, O, E, Ex>(this, b)

inline operator fun <I, M, O, E, reified Ex : Throwable> (UnsafeFlange<I, M, E, Ex>).div(b: UnsafeFlange<M, O, E, Ex>) =
    pipe<I, M, O, E, Ex>(this, b)

inline operator fun <I, M, O, E, reified Ex : Throwable> (UnsafeFlange<I, M, E, Ex>).div(b: Aqueduct<M, O, E, Ex>) =
    pipe<I, M, O, E, Ex>(this, b)

operator fun <I, M, O, E, Ex : Throwable> (Aqueduct<I, M, E, Ex>).div(b: SafeFlange<M, O, E>) =
    pipe<I, M, O, E, Ex>(this, b)

inline operator fun <I, M, O, E, reified Ex : Throwable> (Aqueduct<I, M, E, Ex>).div(b: UnsafeFlange<M, O, E, Ex>) =
    pipe<I, M, O, E, Ex>(this, b)

inline operator fun <I, M, O, E, reified Ex : Throwable> (Aqueduct<I, M, E, Ex>).div(b: Aqueduct<M, O, E, Ex>) =
    pipe<I, M, O, E, Ex>(this, b)

//// I, O, E, Nothing
operator fun <I, M, O, E> (SafeFlange<I, M, E>).div(b: suspend PipelineContext<M, O, E>.() -> Unit) =
    pipe<I, M, O, E>(this, SafeFlange<M, O, E>(b))

inline operator fun <I, M, O, E, reified Ex : Throwable> (UnsafeFlange<I, M, E, Ex>).div(noinline b: suspend PipelineContext<M, O, E>.() -> Unit) =
    pipe<I, M, O, E, Ex>(this, SafeFlange<M, O, E>(b))

inline operator fun <I, M, O, E, Ex : Throwable> (Aqueduct<I, M, E, Ex>).div(noinline b: suspend PipelineContext<M, O, E>.() -> Unit) =
    pipe<I, M, O, E, Ex>(this, SafeFlange<M, O, E>(b))

//////////////////////////////////////////////////////////// Collecting

object Collect
object CollectSafe

operator fun <O, E, Ex : Throwable> Rinsable<Nothing, O, E, Ex>.div(collector: Collect): List<O> = shell(this)

operator fun <O, E, Ex : Throwable> Rinsable<Nothing, O, E, Ex>.div(collector: CollectSafe): Either<List<@UnsafeVariance O>, Ex> =
    safeShell(this)

//////////////////////////////////////////////////////////// Subshell sugar

data class PipelineContextDollar<I, O, E>(val context: PipelineContext<I, O, E>) {
    suspend inline operator fun <reified Ex : Throwable> invoke(subshell: Rinsable<I, O, E, Ex>) =
        context.eval(subshell)

    // Copy-paste from Dollar
    inline fun <O, E, reified Ex : Throwable> unsafeDirty(crossinline func: suspend PipelineContext<Nothing, O, E>.() -> Unit) =
        Dollar.unsafeDirty<_, _, Ex>(func)

    inline fun <O, reified Ex : Throwable> unsafe(crossinline func: suspend PipelineContext<Nothing, O, Nothing>.() -> Unit) =
        Dollar.unsafe<_, Ex>(func)

    fun <O, E> dirty(func: suspend PipelineContext<Nothing, O, E>.() -> Unit) = Dollar.dirty(func)
    operator fun <O> invoke(func: suspend PipelineContext<Nothing, O, Nothing>.() -> Unit) = Dollar.invoke(func)
}

val <I, O, E> PipelineContext<I, O, E>.`$` get() = PipelineContextDollar(this)
val <I, O, E> PipelineContext<I, O, E>.S get() = PipelineContextDollar(this)

data class PipelineContextCat<I, O, E>(val context: PipelineContext<I, O, E>)

val <I, O, E> PipelineContext<I, O, E>.cat get() = PipelineContextCat(this)

suspend inline operator fun <I, O, E, reified Ex : Throwable> Rinsable<Nothing, O, E, Ex>.div(cat: PipelineContextCat<I, O, E>): Unit =
    cat.context.subshell(this)

//////////////////////////////////////////////////////////// Special functions
////////////////////////////// Parameters establishing

fun <I> echo(vararg elements: I) = SafeFlange<Nothing, I, Nothing> {
    for (e in elements) send(e)
}

val echo_n = SafeFlange<Nothing, Nothing, Nothing> { }

inline fun <IO, E, reified Ex : Throwable> beware() = UnsafeFlange<IO, IO, E, Ex> {
    for (e in input) send(e)
}

fun <IO, E> expect() = SafeFlange<IO, IO, E> {
    for (e in input) send(e)
}

////////////////////////////// Detension basin

object Exclamation

typealias i = Exclamation
typealias `!` = Exclamation

operator fun <I, O, E> (SafeFlange<I, O, E>).div(b: Exclamation) = this / barrier()
inline operator fun <I, O, E, reified Ex : Throwable> (UnsafeFlange<I, O, E, Ex>).div(b: Exclamation) = this / barrier()

operator fun <I, O, E, Ex : Throwable> (Aqueduct<I, O, E, Ex>).div(b: Exclamation) = this / barrier()

//////////////////////////////////////////////////////////// Redirecting values

//operator fun <O, E, Ex : Throwable, R> Rinsable<Nothing, O, E, Ex>.div(func: (List<O>) -> R) = func(this / Collect)

//operator fun <O, E, Ex : Throwable, R> Rinsable<Nothing, O, E, Ex>.rem(func: (O) -> R) = (this / Collect).map(func)
//
//operator fun <T, R> T.times(func: (T) -> R) = func (this)
//operator fun <T, R> T.rem(func:(T) -> R) = func(this)

//////////////////////////////////////////////////////////// Redirecting values

suspend fun logCrash(str: String, func: suspend () -> Unit) {
    try {
        func()
        println("Noncrash $str")
    } catch (e: CancellationException) {
        println("Crash $str")
        throw e
    }
}

fun main() {
    `$`.unsafeDirty<_, _, IllegalArgumentException> {
        name("a")
        send(1)
        echo_n / expect<_, Int>() / dirty {
            name("b")
            send(1)
            send(2)
            sendError(1)
            send(3)
            send(4)
            send(5)
            finished()
        } / `!` / beware<_, _, IllegalArgumentException>() / unsafe {
            name("c")
            send(receive().let { it * it })
//                                    throw AssertionError()
            send(receive().let { it * it })
            send(receive().let { it * it })
//                    throw IllegalArgumentException()
            finished()
        } / cat
        send(2)
        send(3)
        finished()
    } / {
        name("d")
        send(receive().let { it * it })
        send(receive().let { it * it })
        send(receive().let { it * it })
        finished()
    }//  % ::println * ::println
}

