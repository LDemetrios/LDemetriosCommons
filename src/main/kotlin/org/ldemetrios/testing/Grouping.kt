package org.ldemetrios.testing

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.spec.style.scopes.FreeSpecContainerScope
import org.ldemetrios.utilities.isStatic
import java.io.IOException
import java.nio.file.Files
import kotlin.io.path.absolutePathString

sealed interface Tests<T> {
    suspend fun execute(function: (T) -> Unit, scope: FreeSpecContainerScope)

    fun execute(function: (T) -> Unit, scope: FreeSpec)

    fun register(list: List<String>, value: T): Boolean
}

data class SingleTest<T>(val name: String, val value: T) : Tests<T> {
    override suspend fun execute(function: (T) -> Unit, scope: FreeSpecContainerScope) {
        with(scope) {
            name {
                function(value)
            }
        }
    }

    override fun execute(function: (T) -> Unit, scope: FreeSpec) {
        with(scope) {
            name {
                function(value)
            }
        }
    }

    override fun register(list: List<String>, value: T): Boolean = false
}

data class TestGroup<T>(val ctxName: String?, val subs: MutableMap<String, Tests<T>> = mutableMapOf()) : Tests<T> {
    override suspend fun execute(function: (T) -> Unit, scope: FreeSpecContainerScope) = with(scope) {
        if (ctxName != null) {
            ctxName - {
                subs.values.forEach {
                    it.execute(function, this)
                }
            }
        } else {
            subs.values.forEach {
                it.execute(function, scope)
            }
        }
    }

    override fun execute(function: (T) -> Unit, scope: FreeSpec) = with(scope) {
        if (ctxName != null) {
            ctxName - {
                subs.values.forEach {
                    it.execute(function, this)
                }
            }
        } else {
            subs.values.forEach {
                it.execute(function, scope)
            }
        }
    }

    override fun register(list: List<String>, value: T): Boolean {
        if (list.isEmpty()) throw AssertionError()
        if (list.size == 1) {
            if (list[0] in subs) return false
            subs[list[0]] = SingleTest(list[0], value)
            return true
        } else {
            return subs.computeIfAbsent(list[0]) {
                TestGroup(list[0])
            }.register(list.drop(1), value)
        }
    }
}

context(FreeSpec) fun <T> runTestsGrouping(
    cases: Sequence<T>,
    nameSelector: (T) -> List<String>,
    func: (T) -> Unit
) {
    val group = TestGroup<T>(null)

    for (i in cases) {
        val name = nameSelector(i)
        if (!group.register(name, i)) {
            throw AssertionError("Couldn't register $name, already present")
        }
    }

    group.execute(func, implicit())
}

fun <A> A.implicit() = this
