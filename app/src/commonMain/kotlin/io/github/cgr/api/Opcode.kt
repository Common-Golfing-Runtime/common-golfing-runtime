package io.github.cgr.api

interface Opcode {

    companion object {
        operator fun get(opcode: String): Opcode = opcodes[opcode] ?: throw IllegalArgumentException("Unknown opcode: $opcode")
    }

    val name: String
    val argLength: Int

    fun execute(args: IntArray, vm: VirtualMachine)

    fun register() {
        opcodes[name] = this
    }
}

private val opcodes = mutableMapOf<String, Opcode>()