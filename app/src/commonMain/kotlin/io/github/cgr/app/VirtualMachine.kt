package io.github.cgr.app

class VirtualMachine(private val program: Program) {

    val stack = ArrayDeque<Any?>()

    var programCounter = 0
        private set

    fun run() {
        while (programCounter < program.opcodes.size) {
            val (opcode, args) = program.opcodes[programCounter++]
            opcode.execute(args, this)
        }
    }

    fun jump(address: Int) {
        programCounter = address - 1 // -1 because pc will be incremented at the end of the loop
    }

    fun jumpRelative(offset: Int) {
        programCounter += offset - 1 // -1 because pc will be incremented at the end of the loop
    }
}

fun <T> ArrayDeque<T>.pop(): T = removeLast()

fun <T> ArrayDeque<T>.push(value: T) = addLast(value)

fun <T> ArrayDeque<T>.peek(): T = last()