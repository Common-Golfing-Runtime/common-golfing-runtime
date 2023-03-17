package io.github.cgr.app

class VirtualMachine(private val program: Program) {

    var stack = ArrayDeque<Any?>()

    private var pc = 0

    fun run() {
        while (pc < program.opcodes.size) {
            val (opcode, args) = program.opcodes[pc++]
            opcode.execute(args, this)
        }
    }

    fun jump(address: Int) {
        pc = address - 1 // -1 because pc will be incremented at the end of the loop
    }

    fun jumpRelative(offset: Int) {
        pc += offset - 1 // -1 because pc will be incremented at the end of the loop
    }

    fun programCounter() = pc
}

fun <T> ArrayDeque<T>.pop(): T = removeLast()

fun <T> ArrayDeque<T>.push(value: T) = addLast(value)

fun <T> ArrayDeque<T>.peek(): T = last()