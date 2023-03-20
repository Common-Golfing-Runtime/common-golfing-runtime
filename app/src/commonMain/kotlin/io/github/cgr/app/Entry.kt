@file:JvmName("Entry")

package io.github.cgr.app

import io.github.cgr.app.io.readProgram
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.jvm.JvmName

@JsExport
@OptIn(ExperimentalJsExport::class)
fun executeFile(file: ByteArray) {
    val program = readProgram(file)
    val vm = VirtualMachine(program)
    vm.run()
}