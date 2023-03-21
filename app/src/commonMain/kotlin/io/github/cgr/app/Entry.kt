@file:JvmName("Entry")

package io.github.cgr.app

import io.github.cgr.app.io.readProgram
import io.github.cgr.app.objects.BaseLabel
import io.github.cgr.app.objects.BaseOpcode
import io.github.cgr.app.objects.ConstantLabel
import io.github.cgr.app.objects.Opcode
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.jvm.JvmName

@JsExport
@OptIn(ExperimentalJsExport::class)
fun executeFile(file: ByteArray) {
    BaseOpcode.values().forEach(Opcode::register)
    BaseLabel.values().forEach(ConstantLabel::register)
    val program = readProgram(file)
    val vm = VirtualMachine(program)
    vm.run()
}