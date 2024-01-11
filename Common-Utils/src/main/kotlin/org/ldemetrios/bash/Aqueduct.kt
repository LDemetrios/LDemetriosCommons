package org.ldemetrios.bash

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

inline fun <I, O, E, reified Ex : Throwable> AutoAqueduct(flanges: List<Flange<*, *, E>>) =
    Aqueduct<I, O, E, Ex>(Ex::class.java, flanges)

data class Aqueduct<in I, out O, out E, out Ex : Throwable> @PublishedApi internal constructor(
    val clazz: Class<out Throwable>,
    val flanges: List<Flange<*, *, E>>,
) : Rinsable<I, O, E, Ex> {
    override fun rinseWith(list: List<I>): Triple<List<O>, List<E>, Ex?> {
        return runBlocking {
            flush(
                this,
                clazz,
                list,
                this@Aqueduct
            )
        }
    }
}

internal suspend fun <I, O, E, Ex : Throwable> flush(
    scope: CoroutineScope,
    clazz: Class<out Throwable>,
    inputData: List<I>,
    aqueduct: Aqueduct<I, O, E, Ex>
): Triple<List<O>, List<E>, Ex?> {
    val flanges: List<Flange<*, *, E>> = aqueduct.flanges
    val input = Channel<Any?>(inputData.size)
    for (el in inputData) input.send(el)
    input.close()
    val pipes = List(flanges.size + 1) { if (it == 0) input else Channel() }

    val error = Channel<E>(Channel.UNLIMITED)
    var except: Ex? = null

    var jobs: List<Job>? = null

    val cancelAll: () -> Unit = {
        jobs?.forEach(Job::cancel)
        pipes.forEach(Channel<*>::close)
    }

    jobs = flanges.mapIndexed { i, flange ->
        scope.launch {
            val result = try {
                (flange as Flange<Any?, Any?, E>)(PipelineContext(pipes[i], pipes[i + 1], error, this))
                null
            } catch (e: Exception) {
                cancelAll()
                if (clazz.isInstance(e)) {
                    System.err.println("Right: $clazz -- $e")
//                    e.printStackTrace()
                    e as Ex
                } else {
                    System.err.println("Left: not $clazz -- $e")
//                    e.printStackTrace()
                    throw e
                }
            }
            println("out of $flange")
            pipes[i + 1].close()
            if (result != null) {
                except = result
                log("$flange: non zero res $result, cancel all") { cancelAll() }
            }
            if (i == flanges.lastIndex) {
                log("$flange: that is last, cancel all") { cancelAll() }
            }
            println("$flange's last word")
        }
    }

    println("main: main")
    val lastOut = pipes.last().toList()
    println("main: $lastOut")
    error.close()
    val errorOut = error.toList()
    println("--- ErrorList: $errorOut")
    return Triple(
        lastOut.map { it as O },
        errorOut,
        except
    )
}

fun <I, O, E> AqueductOf(flange: SafeFlange<I, O, E>) = Aqueduct<I, O, E, Nothing>(Nothing::class.java, listOf(flange))
inline fun <I, O, E, reified Ex : Throwable> AqueductOf(flange: UnsafeFlange<I, O, E, Ex>) =
    Aqueduct<I, O, E, Nothing>(Ex::class.java, listOf(flange))


fun <I, M, O, E> pipe(a: SafeFlange<I, M, E>, b: SafeFlange<M, O, E>) =
    Aqueduct<I, O, E, Nothing>(Nothing::class.java, listOf(a, b))

inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: SafeFlange<I, M, E>, b: UnsafeFlange<M, O, E, Ex>) =
    Aqueduct<I, O, E, Ex>(Ex::class.java, listOf(a, b))

inline fun <I, M, O, E, Ex : Throwable> pipe(a: SafeFlange<I, M, E>, b: Aqueduct<M, O, E, Ex>) =
    Aqueduct<I, O, E, Ex>(b.clazz, listOf(a) + b.flanges)


inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: UnsafeFlange<I, M, E, Ex>, b: SafeFlange<M, O, E>) =
    Aqueduct<I, O, E, Ex>(Ex::class.java, listOf(a, b))

inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: UnsafeFlange<I, M, E, Ex>, b: UnsafeFlange<M, O, E, Ex>) =
    Aqueduct<I, O, E, Ex>(Ex::class.java, listOf(a, b))

inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: UnsafeFlange<I, M, E, Ex>, b: Aqueduct<M, O, E, Ex>) =
    Aqueduct<I, O, E, Ex>(Ex::class.java, listOf(a) + b.flanges)


inline fun <I, M, O, E, Ex : Throwable> pipe(a: Aqueduct<I, M, E, Ex>, b: SafeFlange<M, O, E>) =
    Aqueduct<I, O, E, Ex>(a.clazz, a.flanges + b)

inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: Aqueduct<I, M, E, Ex>, b: UnsafeFlange<M, O, E, Ex>) =
    Aqueduct<I, O, E, Ex>(Ex::class.java, a.flanges + b)

inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: Aqueduct<I, M, E, Ex>, b: Aqueduct<M, O, E, Ex>) =
    Aqueduct<I, O, E, Ex>(Ex::class.java, a.flanges + b.flanges)

////// And now possibilities for Safe Aqueducts, because reified doesn't allow Nothing
//
//inline fun <I, M, O, E> pipe(a: SafeFlange<I, M, E>, b: Aqueduct<M, O, E, Nothing>) =
//    Aqueduct<I, O, E, Nothing>(Nothing::class.java, listOf(a) + b.flanges)
//
//
//inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: UnsafeFlange<I, M, E, Ex>, b: Aqueduct<M, O, E, Nothing>) =
//    Aqueduct<I, O, E, Ex>(Ex::class.java, listOf(a) + b.flanges)
//
//inline fun <I, M, O, E> pipe(a: Aqueduct<I, M, E, Nothing>, b: SafeFlange<M, O, E>) =
//    Aqueduct<I, O, E, Nothing>(Nothing::class.java, a.flanges + b)
//
//inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: Aqueduct<I, M, E, Nothing>, b: UnsafeFlange<M, O, E, Ex>) =
//    Aqueduct<I, O, E, Ex>(Ex::class.java, a.flanges + b)
//
//inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: Aqueduct<I, M, E, Ex>, b: Aqueduct<M, O, E, Nothing>) =
//    Aqueduct<I, O, E, Ex>(Ex::class.java, a.flanges + b.flanges)
//inline fun <I, M, O, E, reified Ex : Throwable> pipe(a: Aqueduct<I, M, E, Nothing>, b: Aqueduct<M, O, E, Ex>) =
//    Aqueduct<I, O, E, Ex>(Ex::class.java, a.flanges + b.flanges)
//inline fun <I, M, O, E> pipe(a: Aqueduct<I, M, E, Nothing>, b: Aqueduct<M, O, E, Nothing>) =
//    Aqueduct<I, O, E, Nothing>(Nothing::class.java, a.flanges + b.flanges)

