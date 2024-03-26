  @file:Suppress("PackageDirectoryMismatch", "unused", "NAME_SHADOWING", "KotlinRedundantDiagnosticSuppress", "UNUSED_PARAMETER", "UNUSED_PARAMETER", "ObjectPropertyName", "NonAsciiCharacters", "RedundantLambdaArrow")  

 package org.ldemetrios.utilities

import java.io.Serializable

interface Tuple<out T> : Serializable {
      fun reify(): List<T>
      val size: Int get() = reify().size
      operator fun get(index: Int) = reify()[index]
      val head: T
      val tail: Tuple<T>
 }

data object None : Tuple<Nothing> {
      private fun readResolve(): Any = None
      override fun reify() = listOf<Nothing>()
      override fun toString(): String = "()"
      
     override val head: Nothing get() = throw NoSuchElementException("head of empty tuple")
      override val tail: Tuple<Nothing> get() = throw NoSuchElementException("tail of empty tuple")
      override val size: Int get() = 0
 }

infix fun <A> None.and(a: A) = Single(a)

data class Single<out O>(val only: O) : Tuple<O> {
      override fun reify() = listOf(only)
      override fun toString(): String = "($only,)"
      
     override val head: O get() = only
      override val tail: Tuple<O> get() = None
      override val size: Int get() = 1
      val first get() = only }

infix fun <C, F : C, S : C> Single<F>.and(last: S) = Couple(first, last)

data class Couple<out C, out F : C, out S : C>(val first : F,val second : S,) : Tuple<C> {
     override fun reify() = listOf(first,second,)
     override fun toString(): String = "($first, $second)"

     override val head = first
     override val tail = Single (second, )

     override val size = 2;}

infix fun <C, F : C, S : C, T : C, > Couple<C, F, S>.and(last: T) = Trio (first, second, last)

data class Trio<out C, out F : C, out S : C, out T : C>(val first : F,val second : S,val third : T,) : Tuple<C> {
     override fun reify() = listOf(first,second,third,)
     override fun toString(): String = "($first, $second, $third)"

     override val head = first
     override val tail = Couple (second, third, )

     override val size = 3;}

infix fun <C, F : C, S : C, T : C, Q : C, > Trio<C, F, S, T>.and(last: Q) = Quartet (first, second, third, last)

data class Quartet<out C, out F : C, out S : C, out T : C, out Q : C>(val first : F,val second : S,val third : T,val fourth : Q,) : Tuple<C> {
     override fun reify() = listOf(first,second,third,fourth,)
     override fun toString(): String = "($first, $second, $third, $fourth)"

     override val head = first
     override val tail = Trio (second, third, fourth, )

     override val size = 4;}

infix fun <C, F : C, S : C, T : C, Q : C, R : C, > Quartet<C, F, S, T, Q>.and(last: R) = Quintuple (first, second, third, fourth, last)

data class Quintuple<out C, out F : C, out S : C, out T : C, out Q : C, out R : C>(val first : F,val second : S,val third : T,val fourth : Q,val fifth : R,) : Tuple<C> {
     override fun reify() = listOf(first,second,third,fourth,fifth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"

     override val head = first
     override val tail = Quartet (second, third, fourth, fifth, )

     override val size = 5;}

infix fun <C, F : C, S : C, T : C, Q : C, R : C, H : C, > Quintuple<C, F, S, T, Q, R>.and(last: H) = Hexad (first, second, third, fourth, fifth, last)

data class Hexad<out C, out F : C, out S : C, out T : C, out Q : C, out R : C, out H : C>(val first : F,val second : S,val third : T,val fourth : Q,val fifth : R,val sixth : H,) : Tuple<C> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"

     override val head = first
     override val tail = Quintuple (second, third, fourth, fifth, sixth, )

     override val size = 6;}

infix fun <C, F : C, S : C, T : C, Q : C, R : C, H : C, SX : C, > Hexad<C, F, S, T, Q, R, H>.and(last: SX) = Septuple (first, second, third, fourth, fifth, sixth, last)

data class Septuple<out C, out F : C, out S : C, out T : C, out Q : C, out R : C, out H : C, out SX : C>(val first : F,val second : S,val third : T,val fourth : Q,val fifth : R,val sixth : H,val seventh : SX,) : Tuple<C> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"

     override val head = first
     override val tail = Hexad (second, third, fourth, fifth, sixth, seventh, )

     override val size = 7;}

infix fun <C, F : C, S : C, T : C, Q : C, R : C, H : C, SX : C, O : C, > Septuple<C, F, S, T, Q, R, H, SX>.and(last: O) = Octet (first, second, third, fourth, fifth, sixth, seventh, last)

data class Octet<out C, out F : C, out S : C, out T : C, out Q : C, out R : C, out H : C, out SX : C, out O : C>(val first : F,val second : S,val third : T,val fourth : Q,val fifth : R,val sixth : H,val seventh : SX,val eighth : O,) : Tuple<C> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"

     override val head = first
     override val tail = Septuple (second, third, fourth, fifth, sixth, seventh, eighth, )

     override val size = 8;}

infix fun <C, F : C, S : C, T : C, Q : C, R : C, H : C, SX : C, O : C, N : C, > Octet<C, F, S, T, Q, R, H, SX, O>.and(last: N) = Nonuple (first, second, third, fourth, fifth, sixth, seventh, eighth, last)

data class Nonuple<out C, out F : C, out S : C, out T : C, out Q : C, out R : C, out H : C, out SX : C, out O : C, out N : C>(val first : F,val second : S,val third : T,val fourth : Q,val fifth : R,val sixth : H,val seventh : SX,val eighth : O,val ninth : N,) : Tuple<C> {
     override fun reify() = listOf(first,second,third,fourth,fifth,sixth,seventh,eighth,ninth,)
     override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"

     override val head = first
     override val tail = Octet (second, third, fourth, fifth, sixth, seventh, eighth, ninth, )

     override val size = 9;}

 
