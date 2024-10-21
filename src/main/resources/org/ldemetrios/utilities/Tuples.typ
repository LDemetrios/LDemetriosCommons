#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode

package org.ldemetrios.utilities;;;;

import java.io.Serializable;;;;

#let tab = "    "

#let up-to = 20
#let tuple-names = "Nullad, Monad, Dyad, Triad, Tetrad, Pentad, Hexad, Heptad, Octad, Ennead, Decad, Undecad, Dodecad, Tridecad, Quattuordecad, Quindecad, Hexdecad, Heptadecad, Octodecad, Novemdecad, Icosad".split(", ").filter(it => it != "")
#let element-names = "first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth, thirteenth, fourteenth, fifteenth, sixteenth, seventeenth, eighteenth, nineteenth, twentieth".split(", ").filter(it => it != "")
#let typearg-names = "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y".split(", ").filter(it => it != "")
#let common-name = "Z"

interface Tuple\<out T> : Serializable {;;
#tab    fun reify(): List\<T>;;
#tab    val size: Int get() = reify().size;;
#tab    operator fun get(index: Int) = reify()[index];;
#tab    val head: T;;
#tab    val tail: Tuple\<T>;;
#tab    fun \<Next : \@UnsafeVariance T> append(next: Next): Tuple\<T>;;
#tab    fun \<NewFirst : \@UnsafeVariance T> cons(new: NewFirst): Tuple\<T>;;
};;;;

val None = Nullad\<Nothing>();;;;

fun \<Fake> Nullad(list:List\<Fake>) : Nullad\<Fake> {;;
    require(list.isEmpty()) {\"Nullad from non-empty list\"};;
    return None;;
};;;;

class Nullad\<out Fake> : Tuple\<Fake> {;;
#tab    override fun reify() = listOf\<Nothing>();;
#tab    override fun toString(): String = \"()\";; #tab ;;

#tab    override val head: Nothing get() = throw NoSuchElementException(\"head of empty tuple\");;
#tab    override val tail: Tuple\<Nothing> get() = throw NoSuchElementException(\"tail of empty tuple\");;
#tab    override val size: Int get() = 0;;
#tab    override fun \<Next : \@UnsafeVariance Fake> append(next: Next) = Monad(next);;
#tab    override fun \<NewFirst : \@UnsafeVariance Fake> cons(new: NewFirst) = Monad(new);;
#tab    override fun hashCode() = 1;;
#tab    override fun equals(other: Any?) = other is Nullad\<\*>;;
};;;;

infix fun \<Fake, F> Nullad\<Fake>.and(next: F) = Monad(next);;
fun \<Fake, F> cons(head: F, tail: Nullad\<Fake>) = Monad(head);;;;

fun \<O> Monad(list:List\<O>) = Monad(list.single());;;;

data class Monad\<out O>(val only: O) : Tuple\<O> {;;
#tab    override fun reify() = listOf(only);;
#tab    override fun toString(): String = \"(\$only,)\";; #tab ;;

#tab    override val head: O get() = only;;
#tab    override val tail: Tuple\<O> get() = None;;
#tab    override val size: Int get() = 1;;
#tab    val first get() = only;;
#tab    override fun \<Next : \@UnsafeVariance O> append(next: Next) = Dyad(only, next);;
#tab    override fun \<NewFirst : \@UnsafeVariance O> cons(new: NewFirst) = Dyad(new, only);;
};;;;

infix fun \<#common-name, A : #common-name, B : #common-name> Monad\<A>.and(next: B) = Dyad(first, next);;
fun \<#common-name, A : #common-name, B : #common-name> cons(head: A, tail: Monad\<B>) = Dyad(head, tail.first);;;;

#let join-params(from, to, transform /*: (it) => [#it]*/) = {
    [#transform(typearg-names.at(from))]
    for i in range(from + 1, to) {
        [, #transform(typearg-names.at(i))]
    }
}
#let join-named(from, to, transform /*: (it, jt) => [#it : #jt]*/) = {
    [#transform(element-names.at(from), typearg-names.at(from))]
    for i in range(from + 1, to) {
        [, #transform(element-names.at(i), typearg-names.at(i))]
    }
}

#let join-indexed(from, to, transform ) = {
    [#transform(from, typearg-names.at(from))]
    for i in range(from + 1, to) {
        [, #transform(i, typearg-names.at(i))]
    }
}

#let tuple-name(size) = [#tuple-names.at(size)\<#common-name, #join-params(0, size, it => [#it])>]
#let id = (it) => [#it]

#let tuple(n) = {
    [
    fun \<#common-name, #join-params(0, n, it => [#it : #common-name])> #tuple-names.at(n) (list:List\<#common-name>) : #tuple-name(n) {;;
    #tab    require(list.size == #n) {\"#tuple-names.at(n) should be created from list of #n elements\"};;
    #tab    return #tuple-names.at(n) (#join-indexed(0, n, (ind, name) => [list[#ind] as #name])) ;;
    };;;;
    ]

    [data class #tuple-names.at(n)\<out #common-name]
    for i in range(0, n) {
        [, out #typearg-names.at(i) : #common-name]
    }
    [\>\(]
    for i in range(0, n) {
        [val #element-names.at(i) : #typearg-names.at(i),]
    }
    [\) : Tuple\<#common-name> \{;;]

    [#tab override fun reify() = listOf\(]
    for i in range(0, n) {
        [#element-names.at(i),]
    }
    [\);;]

    [#tab override fun toString(): String = \"\(\$first]
    for i in range(1, n) {
        [, \$#element-names.at(i)]
    }
    [\)\";;;;]

    [#tab override val head = first;;]

    [#tab override val tail = #tuple-names.at(n - 1) (]
    for i in range(1, n) {
        [#element-names.at(i), ]
    }
    [\);;]

    [#tab override val size = #n ;;]

    [#tab    override fun \<Next : \@UnsafeVariance #common-name> append(next: Next) = ]

    if (n == up-to) {
        [Myriad(listOf(#join-named(0, n, (name, typ) => [#name]), next));;]
    } else {
        [#tuple-names.at(n + 1) (]
        for i in range(0, n) {
            [#element-names.at(i), ]
        }
        [next\);;]
    }

    [#tab    override fun \<NewFirst : \@UnsafeVariance #common-name> cons(new: NewFirst) = ]

    if (n == up-to) {
        [Myriad(listOf(new, #join-named(0, n, (name, typ) => [#name])));;]
    } else {
        [#tuple-names.at(n + 1) (first, ]
        for i in range(0, n) {
            [#element-names.at(i), ]
        }
        [\);;]
    }

    [};;;;]

    if (n != up-to) {
        [infix fun \<#common-name]
        for i in range(0, n+1) {
            [, #typearg-names.at(i) : #common-name]
        }
        [\> #tuple-names.at(n)\<#common-name]
        for i in range(0, n) {
            [, #typearg-names.at(i)]
        }
        [\>.and\(next: #typearg-names.at(n)) = #tuple-names.at(n+1) \(first]
        for i in range(1, n) {
            [, #element-names.at(i)]
        }
        [, next);;]

        [fun \<#common-name]
        for i in range(0, n+1) {
            [, #typearg-names.at(i) : #common-name]
        }
        [\> cons\(head: #typearg-names.at(0), tail: #tuple-names.at(n)\<#common-name]
        for i in range(1, n+1) {
            [, #typearg-names.at(i)]
        }
        [\>) = #tuple-names.at(n+1) \(head]
        for i in range(0, n) {
            [, tail.#element-names.at(i)]
        }
        [);;;;]
    }
}

#for n in range(2, up-to + 1){
    tuple(n)
}

data class Myriad\<out #common-name>(val elements: List\<#common-name>) : Tuple\<#common-name> {;;
#tab     override fun reify() = elements;;
#tab     override fun toString(): String = elements.joinToString(\", \", \"(\", \")\");;
;;
#tab     override val head = elements[0];;
#tab     override val tail = tupleOf(elements.drop(1));;
#tab     override val size = elements.size;;
#tab     override fun \<Next : \@UnsafeVariance #common-name> append(next: Next) = Myriad(elements + next);;
#tab     override fun \<NewFirst : \@UnsafeVariance #common-name> cons(new: NewFirst) = Myriad(listOf(new) + elements);;
};;;;

fun \<#common-name> tupleOf(list: List\<#common-name>) = if (list.size \> #up-to) Myriad(list) else when(list.size) {;;
#tab    0 -\> None;;
#for i in range(1, up-to + 1) [
#tab    #i -\> #tuple-names.at(i) ( #for j in range(0, i) [list[#j], ] );;
]
#tab    else -\> throw AssertionError();;
};;;;

infix fun \<T, N : T> Tuple\<T>.and(next: N) = this.append(next);;
fun \<T, N : T> cons(head: N, tail: Tuple\<T>) = tail.cons(head);;;;

fun \<From, A> tee (first : (From) -\> A) : (From) -> Monad\<A> = {Monad (first (it)) };;
fun \<A> lift (firstFunc : (A) -\> A) : (Monad\<A>) -> Monad\<A> = {;;
#tab    Monad (firstFunc(it.first));;
};;;;

#for n in range(2, up-to + 1) {
    [fun \<From, #common-name, #join-params(0, n, it => [#it : #common-name])> tee
    \(#join-named(0, n, (it, jt) => [#it : (From) -\> #jt])) :
    (From) -\> #tuple-name(n) =
    \{#tuple-names.at(n) (#join-named(0, n, (name, dc) => [#name \(it\)])) };;

    ]
}
;;;;

#for n in range(2, up-to + 1) {
    [fun \<#common-name, #join-params(0, n, it => [#it : #common-name])> lift
    \(#join-named(0, n, (it, jt) => [#it;Func : (#jt) -\> #jt])) :
    \(#tuple-name(n)) -> #tuple-name(n) = {;;
    #tab    #tuple-names.at(n) \(#join-named(0, n, (name, jt) => [#name;Func(it.#name)]));;};;
    ]
}
