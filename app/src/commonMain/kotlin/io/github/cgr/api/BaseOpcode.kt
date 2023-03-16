package io.github.cgr.api

enum class BaseOpcode(
    override val argLength: Int,
    private val executor: (IntArray, VirtualMachine) -> Unit
) : Opcode {
    NOP(0, { _, _ -> }),
    ;

    override fun execute(args: IntArray, vm: VirtualMachine) = executor(args, vm)
}