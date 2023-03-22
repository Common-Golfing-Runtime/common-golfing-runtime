package io.github.cgr.app

import io.github.cgr.app.objects.BaseOpcode

class VirtualMachine(val program: Program) {

    val stack = ArrayDeque<Any?>()

    var programCounter = 0
        private set

    private val labels = program.opcodes
        .filter { (op, _) -> op == BaseOpcode.LABEL }
        .mapIndexed { i, (_, args) -> args[0] to i }
        .let {
            val arr = IntArray(it.maxOfOrNull(Pair<Int, Int>::first) ?: 0) { -1 }
            it.forEach { (label, index) -> arr[label] = index }
            arr
        }

    fun run() {
        val opcodes = program.opcodes
        while (programCounter < opcodes.size) {
            val (opcode, args) = opcodes[programCounter]
            opcode.execute(args, this)
            programCounter++
        }
    }

    fun jump(label: Int) {
        val index = labels[label]
        if (index == -1) throw IllegalArgumentException("Label $label not found")
        programCounter = index
    }

    fun halt() {
        programCounter = program.opcodes.size
    }
}

fun <T> ArrayDeque<T>.pop(): T = removeLast()

fun <T> ArrayDeque<T>.push(value: T) = addLast(value)

fun <T> ArrayDeque<T>.peek(): T = last()