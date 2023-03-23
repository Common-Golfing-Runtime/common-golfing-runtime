package io.github.cgr.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file

fun main(args: Array<String>) = ArgParser().main(args)

private class ArgParser : CliktCommand() {

    val file by argument(help = "File to run").file(mustExist = true, canBeDir = false, mustBeReadable = true)
    val doAssemble by option("-a", "--assemble", help = "Assemble the given CGR bytecode to the given output file")
        .file(mustExist = false, canBeDir = false, mustBeWritable = false)

    override fun run() {
        val code = file.readBytes()

        // Have to copy as smart casing does not work on delegated properties
        val asm = doAssemble
        if (asm != null) {
            asm.writeBytes(assemble(code.decodeToString()))
        } else {
            executeFile(code)
        }
    }
}