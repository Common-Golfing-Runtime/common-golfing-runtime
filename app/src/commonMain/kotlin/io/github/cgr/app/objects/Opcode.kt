package io.github.cgr.app.objects

import io.github.cgr.app.VirtualMachine

interface Opcode {

    companion object {
        /**
         * Gets the opcode with the given name.
         */
        operator fun get(opcode: String): Opcode = opcodes[opcode] ?: throw IllegalArgumentException("Unknown opcode: $opcode")
    }

    /**
     * The name of the opcode.
     */
    val name: String

    /**
     * The number of arguments the opcode takes. If -1, the opcode takes a variable number of arguments.
     * Any other negative number is invalid.
     */
    val arity: Int

    /**
     * Executes the opcode with the given arguments.
     */
    fun execute(args: IntArray, vm: VirtualMachine)

    /**
     * Registers the opcode in the global opcode registry.
     */
    fun register() {
        opcodes[name] = this
    }

    /**
     * Unregisters the opcode from the global opcode registry.
     */
    fun unregister() {
        opcodes.remove(name)
    }
}

private val opcodes = mutableMapOf<String, Opcode>()