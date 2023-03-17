package io.github.cgr.app.io

import io.github.cgr.app.Program
import io.github.cgr.app.objects.ConstantLabel
import io.github.cgr.app.objects.Opcode

fun readProgram(buffer: ByteArray): Program {
    val unpacker = Unpacker(buffer)
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