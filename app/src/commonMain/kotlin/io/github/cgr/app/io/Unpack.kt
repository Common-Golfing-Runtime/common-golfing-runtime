package io.github.cgr.app.io

import io.github.cgr.app.Program
import io.github.cgr.app.objects.ConstantLabel
import io.github.cgr.app.objects.Opcode

fun readProgram(buffer: ByteArray): Program {
    // aaaaaaaaaaa why does kotlin make me use such convoluted code
    require(buffer.sliceArray(0..3).contentEquals(
        byteArrayOf(0xDE.toByte(), 0xAD.toByte(), 0xC0.toByte(), 0xDE.toByte())
    )) { "Invalid magic number" }
    val unpacker = Unpacker(buffer.sliceArray(2 until buffer.size))
    val symbolTable = readSymbolTable(unpacker)
    val constantPool = readConstantPool(unpacker, symbolTable)
    val opcodes = readOpcodes(unpacker, symbolTable)
    return Program(opcodes, constantPool.toTypedArray())
}

private fun readSymbolTable(unpacker: Unpacker): List<String> {
    val size = unpacker.unpackInt()
    val symbols = mutableListOf<String>()
    for (i in 0 until size) {
        symbols.add(unpacker.unpackString())
    }
    return symbols
}

private fun readConstantPool(unpacker: Unpacker, symbolTable: List<String>): List<Any?> {
    val size = unpacker.unpackInt()
    val constants = mutableListOf<Any?>()
    for (i in 0 until size) {
        constants.add(ConstantLabel[symbolTable[unpacker.unpackInt()]].getConstant(unpacker, constants))
    }
    return constants
}

private fun readOpcodes(unpacker: Unpacker, symbolTable: List<String>): Array<Pair<Opcode, IntArray>> {
    val size = unpacker.unpackInt()
    return Array(size) {
        val opcode = Opcode[symbolTable[unpacker.unpackInt()]]
        val args = if (opcode.arity == -1) {
            val arity = unpacker.unpackInt()
            IntArray(arity) { unpacker.unpackInt() }
        } else if (opcode.arity >= 0) {
            IntArray(opcode.arity) { unpacker.unpackInt() }
        } else {
            IntArray(0)
        }
        Pair(opcode, args)
    }
}