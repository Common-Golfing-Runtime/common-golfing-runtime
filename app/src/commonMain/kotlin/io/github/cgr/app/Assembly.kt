package io.github.cgr.app

import io.github.cgr.app.io.MAGIC_NUMBER
import io.github.cgr.app.io.Packer
import io.github.cgr.app.objects.BaseLabel
import io.github.cgr.app.objects.Opcode

fun assemble(program: String): ByteArray {
    registerBase()
    val opcodes = program.lines()
        .asSequence()
        .map(String::trim)
        .filter(String::isNotEmpty)
        .filter { !it.startsWith('#') }
        .map(::parseLine)
        .toList()
    val constants = opcodes.flatMap { it.second }
        .filter { (it.startsWith('"') && it.endsWith('"')) || (it.startsWith('[') && it.endsWith(']')) }
        .distinct()
    val constantMap = constants.mapIndexed { i, s -> s to i }.toMap()
    val (builtPool, labels) = buildConstantPool(constants.map { it.substring(1, it.length - 1) })
    val symbolTable = (labels + opcodes.map { it.first }).distinct()
    val symbolMap = symbolTable.mapIndexed { i, s -> s to i }.toMap()
    val packer = Packer()
    packer.pack(symbolTable.size)
    for (symbol in symbolTable) {
        packer.pack(symbol)
    }
    // mmm glorious array concatenation
    return MAGIC_NUMBER + packer.toByteArray() + builtPool + buildCode(opcodes, constantMap, symbolMap)
}

private val numberRegex = """^(-?\d+)(\.\d+)?$""".toRegex()

private fun parseLine(line: String): Pair<String, List<String>> {
    val split = line.split(' ')
    val opcode = split.first().uppercase()
    val tempArgs = split.drop(1)
    val args = mutableListOf<String>()
    val sb = StringBuilder()
    for (arg in tempArgs) {
        if (arg.startsWith('"') && !arg.endsWith('"')) {
            sb.append(arg)
            continue
        } else if (arg.endsWith('"') && !arg.startsWith('"')) {
            sb.append(' ').append(arg)
            args.add(sb.toString())
            sb.clear()
            continue
        }
        if (sb.isNotEmpty()) {
            sb.append(' ').append(arg)
        } else {
            args.add(arg)
        }
    }
    return opcode to args
}

private fun buildConstantPool(constantPool: List<String>): Pair<ByteArray, List<String>> {
    val packer = Packer()
    packer.pack(constantPool.size)
    val labels = mutableListOf<String>()
    for (constant in constantPool) {
        if (numberRegex.matches(constant)) {
            packer.pack(labels.indexOfOrAdd(BaseLabel.DECIMAL.name))
        } else {
            packer.pack(labels.indexOfOrAdd(BaseLabel.STRING.name))
        }
        packer.pack(constant)
    }
    return packer.toByteArray() to labels
}

private fun buildCode(
    opcodes: List<Pair<String, List<String>>>,
    constantMap: Map<String, Int>,
    symbolMap: Map<String, Int>
): ByteArray {
    val packer = Packer()
    packer.pack(opcodes.size)
    for ((opcode, args) in opcodes) {
        packer.pack(symbolMap[opcode]!!)
        if (Opcode[opcode].arity == -1) {
            packer.pack(args.size)
        }
        for (arg in args) {
            if (arg in constantMap) {
                packer.pack(constantMap[arg]!!)
            } else {
                packer.pack(arg.toInt())
            }
        }
    }
    return packer.toByteArray()
}

private fun <T> MutableList<T>.indexOfOrAdd(element: T): Int {
    val index = indexOf(element)
    if (index == -1) {
        add(element)
        return size - 1
    }
    return index
}