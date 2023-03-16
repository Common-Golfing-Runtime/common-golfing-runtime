package io.github.cgr.api

data class Program(val opcodes: Array<Pair<Opcode, IntArray>>, val constantPool: Array<Any?>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Program) return false
        return opcodes.contentEquals(other.opcodes) && constantPool.contentEquals(other.constantPool)
    }

    override fun hashCode(): Int {
        var result = opcodes.hashCode()
        result = 31 * result + constantPool.contentHashCode()
        return result
    }
}
