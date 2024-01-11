package org.ldemetrios.bash


//internal suspend fun flush1(
//    scope: CoroutineScope,
//    first: Conduit<*, *, *, Any>,
//): Triple<List<Any?>, List<Any?>, Any?> {
//    val input = Channel<Nothing>()
//    input.close()
//
//    val output = Channel<Any?>()
//    val error = Channel<Any?>(Int.MAX_VALUE)
//
//    var firstJob: Job? = null
//    var except: Any? = null
//
//    fun cancelAll() {
//        firstJob?.cancel()
//
//        output.close()
//    }
//
//    firstJob = scope.launch {
//        val result = first(PipelineContext(input, output, error, this))
//        println("out of all")
//        output.close()
//        if (result != null) {
//            except = result
//            cancelAll()
//        }
//        println("last word all")
//    }
//
//    val lastOut = log("main: output.toList()"){ output.toList() }
//    error.close()
//    val errorOut = log("main: error.toList()") { error.toList() }
//    firstJob.cancel()
//    return Triple(
//        lastOut,
//        errorOut,
//        except
//    )
//}
//suspend fun <I, O, E, Except : Any> Conduit<I, O, E, Except>.flow(scope: CoroutineScope): Triple<List<O>, List<E>, Except?> {
//    val inp = Channel<I>()
//    inp.close()
//    val outp = Channel<O>(Int.MAX_VALUE)
//    val err = Channel<E>(Int.MAX_VALUE)
//    var res: Except? = null
//    scope.launch {
//        res = this@flow(PipelineContext(inp, outp, err, this))
////        err.close()
//    }.join()
//    return Triple(
//        outp.toList(),
//        listOf(),
//        res
//    )
//}

//fun <I, O, E, Except : Any> rinse(conduit: Conduit<I, O, E, Except>) = runBlocking {
//    val inp = Channel<I>()
//    inp.close()
//    val outp = Channel<O>(Int.MAX_VALUE)
//    val err = Channel<E>(Int.MAX_VALUE)
//    var res: Except? = null
//    this.launch {
//        res = conduit(PipelineContext<I, O, E>(inp, outp, err, this))
//        err.close()
//    }.join()
//    Triple<List<O>, List<E>, Except?>(
//        outp.toList<O>(),
//        err.toList(),
//        res
//    )
//}
//internal suspend fun <I, M, O, E, Except : Any> pipe(
//    first: Conduit<I, M, E, Except>,
//    other: Conduit<M, O, E, Except>,
//    name: String, anotherName: String
//): Conduit<I, O, E, Except> = Conduit {
//    val pipe = Channel<M>()
//    var ajob: Job? = null
//    var bjob: Job? = null
//    var res: Except? = null
//    ajob = it.launch {
//        val ares = first(PipelineContext(it.input, pipe, it.error, this))
//        println("out of $name")
//        log("trying close $name's outer pipe") { pipe.close() }
//        if (ares != null) {
//            bjob?.cancel()
//            res = ares
//        }
//        println("$name's last word")
//    }
//    var bres: Except? = null
//    bjob = it.launch {
//        bres = other(PipelineContext(pipe, it.output, it.error, this))
//        println("out of $anotherName")
//        ajob.cancel()
//        res = if (res != null) res else bres
//        println("$anotherName's last word")
//    }
//    log("$name&$anotherName: bjob.join()") { bjob.join() }
//    res = if (res != null) res else bres
//    res
//}
//
//fun <I, M, O, E, E1 : E, E2 : E, Except : Any, Except1 : Except, Except2 : Except> pipe(
//    a: Conduit<I, M, E1, Except1>, b: Conduit<M, O, E2, Except2>
//): Conduit<I, O, E, Except> = line {
//    val pipe = Channel<M>()
//    var ajob: Job? = null
//    var bjob: Job? = null
//    var res: Except? = null
//    ajob = launch {
//        val ares = a(PipelineContext(input, pipe, error, this))
//        if (ares != null) {
////            bjob?.cancel()
//            res = ares
//        }
//    }
//    var bres: Except? = null
//    launch {
//        bres = b(PipelineContext(pipe, output, error, this))
//    }.join()
//    res = if (res != null) res else bres
//    res
//}
//
//
//suspend fun <I, O, E, Except : Any> Conduit<I, O, E, Except>.flow(scope: CoroutineScope): Triple<List<O>, List<E>, Except?> {
//    val inp = Channel<I>()
//    inp.close()
//    val outp = Channel<O>(Int.MAX_VALUE)
//    val err = Channel<E>(Int.MAX_VALUE)
//    var res: Except? = null
//    scope.launch {
//        res = this@flow(PipelineContext(inp, outp, err, this))
////        err.close()
//    }.join()
//    return Triple(
//        outp.toList(),
//        listOf(),
//        res
//    )
//}
//
//fun <I, O, E, Except : Any> rinse(conduit: Conduit<I, O, E, Except>) = runBlocking {
//    val inp = Channel<I>()
//    inp.close()
//    val outp = Channel<O>(Int.MAX_VALUE)
//    val err = Channel<E>(Int.MAX_VALUE)
//    var res: Except? = null
//    this.launch {
//        res = conduit(PipelineContext<I, O, E>(inp, outp, err, this))
//        err.close()
//    }.join()
//    Triple<List<O>, List<E>, Except?>(
//        outp.toList<O>(),
//        err.toList(),
//        res
//    )
//}
//
//fun <O, E, Except : Any> S(block: suspend PipelineContext<Nothing, O, E>.() -> Except?) = line(block)
//
//
//fun main() {
//
//    val a =
//        pipe(
//            pipe(
//                pipe(
//                    S<String, Int, Int> {
//                        println("a started")
//                        send("a1")
//                        error(1)
//                        send("a2")
//                        3
//                    },
//                    Conduit<String, List<String>, Int, Int> {
//                        println("b started")
//                        val input = it.input.toList()
//                        println("that was $input")
//                        it.send(input)
//                        null
//                    }
//                ),
//                Conduit<List<String>, String, Int, Int> {
//                    println("c started")
//                    val input = it.receive()
//                    for (e in input) {
//                        it.send(e)
//                    }
//                    null
//                },
//            ),
//            Conduit<String, String, Int, Int> {
//                println("d started")
//                it.send(it.receive())
//                it.send("d")
//                it.error(2)
//                it.output.close()
//                null
//            }
//        )
////    val a = pipe(
////        S<String, Int, Int> {
////            println("a started")
////            send("a1")
////            error(1)
////            send("a2")
////            3
////        },
////        Conduit<String, String, Int, Int> {
////            println("b started")
////            val input = it.input.toList()
////            println("that was $input")
////            for(el in input) {
////                it.send(el)
////            }
////            null
////        }
////    )
//    val (out, err, res) = rinse(a)
//    println(out)
//    println(err)
//    println(res)
//    println("done")
//}
