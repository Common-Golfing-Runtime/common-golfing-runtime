package io.github.cgr.app.objects

import io.github.cgr.app.VirtualMachine
import io.github.cgr.app.peek
import io.github.cgr.app.pop
import io.github.cgr.app.push
import io.github.cgr.glib.impls.asString
import io.github.cgr.glib.impls.gPrint
import io.github.cgr.glib.impls.gPrintln
import io.github.cgr.glib.number.toComplex

enum class BaseOpcode(
    private val executor: (IntArray, VirtualMachine) -> Unit,
    override val arity: Int = 0
) : Opcode {
    NOP({ _, _ -> }),
    HALT({ _, vm -> vm.halt() }),

    // Stack
    POP(stackOp { it.pop() }),
    DUP(stackOp { it.push(it.peek()) }),
    SWAP(stackOp {
        val a = it.pop()
        val b = it.pop()
        it.push(a)
        it.push(b)
    }),
    SIZE(stackOp { it.push(it.size.toComplex()) }),
    LOADC({ args, vm -> vm.stack.push(vm.program.constantPool[args[0]]) }, 1),

    // Control flow
    LABEL({ _, _ -> }, 1),
    JUMP({ args, vm -> vm.jump(args[0]) }, 1),

    // I/O
    PRINT(stackOp { gPrintln(it.pop()) }),
    RPRINT(stackOp { gPrint(it.pop()) }),

    // Golfing stuff
    TO_STRING(monad(::asString)),
    ;

    override fun execute(args: IntArray, vm: VirtualMachine) = executor(args, vm)
}

private inline fun stackOp(crossinline op: (ArrayDeque<Any?>) -> Unit): (IntArray, VirtualMachine) -> Unit =
    { _, vm -> op(vm.stack) }

private inline fun <reified T> monad(crossinline op: (T) -> Any?): (IntArray, VirtualMachine) -> Unit =
    stackOp { it.push(op(it.pop() as T)) }

private inline fun <reified A, reified B> dyad(crossinline op: (A, B) -> Any?): (IntArray, VirtualMachine) -> Unit =
    stackOp { it.push(op(it.pop() as A, it.pop() as B)) }