@file:Suppress("unused", "PackageDirectoryMismatch")

package org.ldemetrios.utilities.aliases

typealias Function<T, R> = (T) -> R
typealias BiFunction<T, U, R> = (T, U) -> R
typealias Consumer<T> = (T) -> Unit
typealias Supplier<T> = () -> T
typealias Predicate<T> = (T) -> Boolean
typealias BiPredicate<T, U> = (T, U) -> Boolean
typealias IntPredicate = (Int) -> Boolean
typealias LongPredicate = (Long) -> Boolean
typealias DoublePredicate = (Double) -> Boolean
typealias ObjIntConsumer<T> = (T, Int) -> Unit
typealias ObjLongConsumer<T> = (T, Long) -> Unit
typealias ObjDoubleConsumer<T> = (T, Double) -> Unit
typealias IntSupplier = () -> Int
typealias LongSupplier = () -> Long
typealias DoubleSupplier = () -> Double
typealias IntFunction<R> = (Int) -> R
typealias LongFunction<R> = (Long) -> R
typealias DoubleFunction<R> = (Double) -> R
typealias IntToDoubleFunction = (Int) -> Double
typealias IntToLongFunction = (Int) -> Long
typealias LongToIntFunction = (Long) -> Int
typealias DoubleToIntFunction = (Double) -> Int
typealias DoubleToLongFunction = (Double) -> Long
typealias IntUnaryOperator = (Int) -> Int
typealias LongUnaryOperator = (Long) -> Long
typealias DoubleUnaryOperator = (Double) -> Double
typealias IntBinaryOperator = (Int, Int) -> Int
typealias LongBinaryOperator = (Long, Long) -> Long
typealias DoubleBinaryOperator = (Double, Double) -> Double
typealias BiConsumer<T, U> = (T, U) -> Unit
typealias BinaryOperator<T> = (T, T) -> T
typealias BooleanSupplier = () -> Boolean
typealias DoubleConsumer = (Double) -> Unit
typealias IntConsumer = (Int) -> Unit
typealias LongConsumer = (Long) -> Unit
typealias LongToDoubleFunction = (Long) -> Double
typealias ToDoubleBiFunction<T, U> = (T, U) -> Double
typealias ToDoubleFunction<T> = (T) -> Double
typealias ToIntBiFunction<T, U> = (T, U) -> Int
typealias ToIntFunction<T> = (T) -> Int
typealias ToLongBiFunction<T, U> = (T, U) -> Long
typealias ToLongFunction<T> = (T) -> Long
typealias UnaryOperator<T> = (T) -> T
typealias Runnable = () -> Unit

fun <T, U> ((T, U) -> Unit).accept(t: T, u: U) = this(t, u)
fun <T, U> ((T, U) -> Boolean).test(t: T, u: U) = this(t, u)
fun <T, U, R> ((T, U) -> R).apply(t: T, u: U) = this(t, u)
fun <T> ((T, T) -> T).apply(t: T, u: T) = this(t, u)
fun (() -> Boolean).getAsBoolean() = this()
fun <T> ((T) -> Unit).accept(t: T) = this(t)
fun ((Double) -> Boolean).test(d: Double) = this(d)
fun (() -> Double).getAsDouble() = this()
fun ((Double) -> Int).applyAsInt(d: Double) = this(d)
fun ((Double) -> Long).applyAsLong(d: Double) = this(d)
fun ((Double) -> Double).applyAsDouble(d: Double) = this(d)
fun ((Double, Double) -> Double).applyAsDouble(t: Double, u: Double) = this(t, u)
fun ((Double) -> Unit).accept(d: Double) = this(d)
fun <R> ((Double) -> R).apply(double: Double) = this(double)
fun <T, R> ((T) -> R).apply(t: T) = this(t)
fun ((Int) -> Boolean).test(i: Int) = this(i)
fun ((Int) -> Unit).accept(i: Int) = this(i)
fun (() -> Int).getAsInt() = this()
fun ((Int) -> Double).applyAsDouble(i: Int) = this(i)
fun ((Int) -> Long).applyAsLong(i: Int) = this(i)
fun ((Int, Int) -> Int).applyAsInt(t: Int, u: Int) = this(t, u)
fun ((Int) -> Int).applyAsInt(i: Int) = this(i)
fun <R> ((Int) -> R).apply(i: Int) = this(i)
fun ((Long) -> Boolean).test(l: Long) = this(l)
fun (() -> Long).getAsLong() = this()
fun ((Long) -> Int).applyAsInt(l: Long) = this(l)
fun ((Long) -> Long).applyAsLong(l: Long) = this(l)
fun ((Long, Long) -> Long).applyAsLong(t: Long, u: Long) = this(t, u)
fun ((Long) -> Unit).accept(l: Long) = this(l)
fun ((Long) -> Double).applyAsDouble(l: Long) = this(l)
fun <R> ((Long) -> R).apply(l: Long) = this(l)
fun <T> ((T, Int) -> Unit).accept(t: T, i: Int) = this(t, i)
fun <T> ((T, Long) -> Unit).accept(t: T, l: Long) = this(t, l)
fun <T> ((T, Double) -> Unit).accept(t: T, d: Double) = this(t, d)
fun <T> ((T) -> Boolean).test(t: T) = this(t)
fun () -> Unit.run() = this()
fun <T> (() -> T).get() = this()
fun <T, U> ((T, U) -> Double).applyAsDouble(t: T, u: U) = this(t, u)
fun <T> ((T) -> Double).applyAsDouble(t: T) = this(t)
fun <T, U> ((T, U) -> Int).applyAsInt(t: T, u: U) = this(t, u)
fun <T> ((T) -> Int).applyAsInt(t: T) = this(t)
fun <T, U> ((T, U) -> Long).applyAsLong(t: T, u: U) = this(t, u)
fun <T> ((T) -> Long).applyAsLong(t: T) = this(t)
fun <T> ((T) -> T).apply(t: T) = this(t)
