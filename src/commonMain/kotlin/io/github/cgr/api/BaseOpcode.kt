package io.github.cgr.api

enum class BaseOpcode(
    override val argLength: Int,
    private val executor: (IntArray, ArrayDeque<Any?>) -> Unit
) : Opcode {
    NOP(0, { _, _ -> }),
    ;

    override fun execute(args: IntArray, stack: ArrayDeque<Any?>) = executor(args, stack)
}