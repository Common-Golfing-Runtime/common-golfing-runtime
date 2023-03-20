package io.github.cgr.app

import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    // TODO actual CLI
    val file = Files.readAllBytes(Path.of(args[0]))
    executeFile(file)
}