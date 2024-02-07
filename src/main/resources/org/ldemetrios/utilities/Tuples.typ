#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode

package org.ldemetrios.utilities;;;;

import java.io.Serializable;;;;

#let tab = "    "

interface Tuple\<out T> : Serializable {;;
#tab    fun reify(): List\<T>;;
#tab    val size: Int get() = reify().size;;
#tab    operator fun get(index: Int) = reify()[index];;
#tab    val head: T;;
#tab    val tail: Tuple\<T>;;
};;;;

data object None : Tuple\<Nothing> {;;
#tab    private fun readResolve(): Any = None;;
#tab    override fun reify() = listOf\<Nothing>();;
#tab    override fun toString(): String = \"()\";; #tab ;;

#tab    override val head: Nothing get() = throw NoSuchElementException(\"head of empty tuple\");;
#tab    override val tail: Tuple\<Nothing> get() = throw NoSuchElementException(\"tail of empty tuple\");;
#tab    override val size: Int get() = 0;;
};;;;

infix fun \<A> None.and(a: A) = Single(a);;;;

data class Single\<out O>(val only: O) : Tuple\<O> {;;
#tab    override fun reify() = listOf(only);;
#tab    override fun toString(): String = \"(\$only,)\";; #tab ;;

#tab    override val head: O get() = only;;
#tab    override val tail: Tuple\<O> get() = None;;
#tab    override val size: Int get() = 1;;
#tab    val first get() = only
};;;;

infix fun \<C, F : C, S : C> Single\<F>.and(last: S) = Couple(first, last);;;;

#let up-to = 10
#let tuple-names = "None, Single, Couple, Trio, Quartet, Quintuple, Hexad, Septuple, Octet, Nonuple, Deka, Hendecad, Dozen".split(", ").filter(it => it != "")
#let element-names = "first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth".split(", ").filter(it => it != "")
#let typearg-names = "F, S, T, Q, R, H, SX, O, N, D, E, DZ".split(", ").filter(it => it != "")

#let tuple(n) = {
    if n != 2 {
        [infix fun \<C, ]
        for i in range(0, n) {
            [#typearg-names.at(i) : C, ]
        }
        [> #tuple-names.at(n - 1)\<C]
        for i in range(0, n - 1) {
            [, #typearg-names.at(i)]
        }
        [>.and(last: #typearg-names.at(n - 1)) = #tuple-names.at(n) (]
        for i in range(0, n - 1) {
            [#element-names.at(i), ]
        }
        [last);;;;]
    }
    [data class #tuple-names.at(n)\<out C]
    for i in range(0, n) {
        [, out #typearg-names.at(i) : C]
    }
    [\>\(]
    for i in range(0, n) {
        [val #element-names.at(i) : #typearg-names.at(i),]
    }
    [\) : Tuple\<C> \{;;]

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
    [\);;;;]

    [#tab override val size = #n;;]
    [};;;;]
}

#for n in range(2, up-to){
    tuple(n)
}