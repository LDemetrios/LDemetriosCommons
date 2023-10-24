@file:Suppress("unused")

package org.ldemetrios.utilities

import java.util.function.*
import java.util.stream.Collector

data class CollectorImpl<T, A, R>(val supplier: () -> A, val accumulator: (A, T) -> Unit, val combiner: (A, A) -> A, val finisher: (A) -> R, val characteristics: Set<Collector.Characteristics>) : Collector<T, A, R> {
    override fun characteristics(): Set<Collector.Characteristics> = characteristics

    override fun supplier(): Supplier<A> = Supplier<A> (supplier)
    override fun accumulator(): BiConsumer<A, T> =  BiConsumer<A, T> (accumulator)
    override fun combiner(): BinaryOperator<A> = BinaryOperator<A> (combiner)
    override fun finisher(): java.util.function.Function<A, R> = java.util.function.Function<A, R> (finisher)
}