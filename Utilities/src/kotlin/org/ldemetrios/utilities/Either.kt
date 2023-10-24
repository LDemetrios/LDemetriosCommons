package org.ldemetrios.utilities

sealed interface Either<out L, out R> {
    fun isLeft(): Boolean
    fun isRight(): Boolean
    fun getLeft(): L
    fun getRight(): R


    fun <T> fold(left: (L) -> T, right: (R) -> T): T = when {
        isLeft() -> left(getLeft()!!)
        isRight() -> right(getRight()!!)
        else -> throw IllegalStateException()
    }

    fun <NL, NR> map(left: (L) -> NL, right: (R) ->NR): Either<NL, NR> = when {
        isLeft() -> left(getLeft()!!)
        isRight() -> right(getRight()!!)
        else -> throw IllegalStateException()
    }

    fun <NL> mapLeft(transform: (L) -> NL): Either<NL, R> = when {
        isLeft() -> Left(transform(getLeft()))
        isRight() -> this as Either<NL, R>
        else -> throw IllegalStateException()
    }


    fun <NR> mapRight(transform: (R) -> NR): Either<L, NR> = when {
        isLeft() -> this as Either<L, NR>
        isRight() -> Right(transform(getRight()))
        else -> throw IllegalStateException()
    }

    fun <T> flatMap(left: (L) -> Either<@UnsafeVariance L, T>, right: (R) -> Either<@UnsafeVariance L, T>): Either<L, T> = when {
        isLeft() -> left(getLeft()!!)
        isRight() -> right(getRight()!!)
        else -> throw IllegalStateException()
    }

    class Left<L>(private val value: L) : Either<L, Nothing> {
        override fun isLeft(): Boolean = true
        override fun isRight(): Boolean = false
        override fun getLeft(): L = value
        override fun getRight(): Nothing = throw IllegalStateException()
    }

    class Right<R>(private val value: R) : Either<Nothing, R> {
        override fun isLeft(): Boolean = false
        override fun isRight(): Boolean = true
        override fun getLeft(): Nothing = throw IllegalStateException()
        override fun getRight(): R = value
    }
}