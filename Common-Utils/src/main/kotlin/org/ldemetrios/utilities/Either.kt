@file:Suppress("unused")

package org.ldemetrios.utilities

import org.ldemetrios.functional.identity

sealed interface Either<out L, out R> {
    fun isLeft(): Boolean
    fun isRight(): Boolean
    fun getLeft(): L
    fun getRight(): R

    fun getLeftOrElse(default: () -> @UnsafeVariance L): L = when {
        isLeft() -> getLeft()
        else -> default()
    }

    fun getRightOrElse(default: () -> @UnsafeVariance R): R = when {
        isRight() -> getRight()
        else -> default()
    }

    fun getLeftOrNull(): L? = when {
        isLeft() -> getLeft()
        else -> null
    }

    fun getRightOrNull(): R? = when {
        isRight() -> getRight()
        else -> null
    }

    fun <NL, NR> map(leftTransform: (L) -> NL, rightTransform: (R) -> NR): Either<NL, NR> = when {
        isLeft() -> Left(leftTransform(getLeft()))
        isRight() -> Right(rightTransform(getRight()))
        else -> throw IllegalStateException()
    }

    fun <NL> mapLeft(transform: (L) -> NL): Either<NL, R> = map(transform, identity())
    fun <NR> mapRight(transform: (R) -> NR): Either<L, NR> = map(identity(), transform)

    fun <T> flatMap(
        left: (L) -> Either<@UnsafeVariance L, T>,
        right: (R) -> Either<@UnsafeVariance L, T>
    ): Either<L, T> = when {
        isLeft() -> left(getLeft()!!)
        isRight() -> right(getRight()!!)
        else -> throw IllegalStateException()
    }

    class Left<L>(private val value: L) : Either<L, Nothing> {
        override fun isLeft(): Boolean = true
        override fun isRight(): Boolean = false
        override fun getLeft(): L = value
        override fun getRight(): Nothing = throw IllegalStateException()
        override fun toString(): String = value.toString()
    }

    class Right<R>(private val value: R) : Either<Nothing, R> {
        override fun isLeft(): Boolean = false
        override fun isRight(): Boolean = true
        override fun getLeft(): Nothing = throw IllegalStateException()
        override fun getRight(): R = value
        override fun toString(): String = value.toString()
    }
}

fun <T, L : T, R : T> Either<L, R>.conjoin() = if (isLeft()) getLeft() else getRight()

fun <L, R> Either<Either<L, R>, R>.flattenLeft(): Either<L, R> = when {
    isLeft() -> getLeft()
    isRight() -> Either.Right(getRight())
    else -> throw IllegalStateException()
}

fun <L, R> Either<L, Either<L, R>>.flattenRight(): Either<L, R> = when {
    isLeft() -> Either.Left(getLeft())
    isRight() -> getRight()
    else -> throw IllegalStateException()
}

fun <L, R> Either<Either<L, R>, Either<L, R>>.flatten(): Either<L, R> = when {
    isLeft() -> getLeft()
    isRight() -> getRight()
    else -> throw IllegalStateException()
}

fun <L, R> Either<L, R>.swap(): Either<R, L> = when {
    isLeft() -> Either.Right(getLeft())
    isRight() -> Either.Left(getRight())
    else -> throw IllegalStateException()
}
