package io.github.cgr.app.objects

import io.github.cgr.app.VirtualMachine
import io.github.cgr.app.peek
import io.github.cgr.app.pop
import io.github.cgr.app.push
import io.github.cgr.glib.impls.asString
import io.github.cgr.glib.impls.gPrint
import io.github.cgr.glib.impls.gPrintln

enum class BaseOpcode(
    override val arity: Int,
    private val executor: (IntArray, VirtualMachine) -> Unit
) : Opcode {
    NOP(0, { _, _ -> }),
    HALT(0, { _, vm -> vm.halt() }),
    POP({ it.pop() }),
    DUP({ it.push(it.peek()) }),
    SWAP({
        val a = it.pop()
        val b = it.pop()
        it.push(a)
        it.push(b)
    }),
    LOADC(1, { args, vm -> vm.stack.push(vm.program.constantPool[args[0]]) }),
    TO_STRING(monad(::asString)),
    PRINT({ gPrintln(it.pop()) }),
    RPRINT({ gPrint(it.pop()) }),
    ;

    constructor(op: (ArrayDeque<Any?>) -> Unit) : this(0, { _, vm -> op(vm.stack) })

    override fun execute(args: IntArray, vm: VirtualMachine) = executor(args, vm)
}

private inline fun monad(crossinline executor: (Any?) -> Any?): (ArrayDeque<Any?>) -> Unit = { it.push(executor(it.pop())) }