package io.github.cgr.app

class VirtualMachine(val program: Program) {

    val stack = ArrayDeque<Any?>()

    var programCounter = 0
        private set

    fun run() {
        val opcodes = program.opcodes
        while (programCounter < opcodes.size) {
            val (opcode, args) = opcodes[programCounter]
            opcode.execute(args, this)
            programCounter++
        }
    }

    fun jump(address: Int) {
        programCounter = address - 1 // -1 because pc will be incremented at the end of the loop
    }

    fun jumpRelative(offset: Int) {
        programCounter += offset - 1 // -1 because pc will be incremented at the end of the loop
    }

    fun halt() {
        programCounter = program.opcodes.size
    }
}

fun <T> ArrayDeque<T>.pop(): T = removeLast()

fun <T> ArrayDeque<T>.push(value: T) = addLast(value)

fun <T> ArrayDeque<T>.peek(): T = last()