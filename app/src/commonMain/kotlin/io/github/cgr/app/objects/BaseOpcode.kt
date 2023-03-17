package io.github.cgr.app.objects

import io.github.cgr.app.VirtualMachine

enum class BaseOpcode(
    override val arity: Int,
    private val executor: (IntArray, VirtualMachine) -> Unit
) : Opcode {
    NOP(0, { _, _ -> }),
    ;

    init {
        register()
    }

    override fun execute(args: IntArray, vm: VirtualMachine) = executor(args, vm)
}