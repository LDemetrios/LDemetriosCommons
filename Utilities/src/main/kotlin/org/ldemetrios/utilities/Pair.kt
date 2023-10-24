@file:Suppress("unused")

package org.ldemetrios.utilities

fun <F, S> lift(f: (F) -> F, s: (S) -> S): (Pair<F, S>) -> Pair<F, S> {
    return { p: Pair<F, S> -> (f(p.first) to s(p.second)) }
}

fun <F, S> lift(f: (F, F) -> F, s: (S, S) -> S): (Pair<F, S>, Pair<F, S>) -> Pair<F, S> {
    return { p1: Pair<F, S>, p2: Pair<F, S> -> f(p1.first, p2.first) to s(p1.second, p2.second) }
}

fun <T, F, S> tee(
    f: (T) -> F,
    s: (T) -> S
): (T) -> Pair<F, S> {
    return { t: T -> (f(t) to s(t)) }
}
