package io.github.cgr.api

import kotlin.reflect.KClass

enum class BaseOpcode(
    override val argLength: Int,
    private val executor: (IntArray, ArrayDeque<Any?>) -> Unit,
    vararg args: KClass<*>
) : Opcode {
    NOP(0, { _, _ -> }),
    ;

    override fun execute(args: IntArray, stack: ArrayDeque<Any?>) = executor(args, stack)
}