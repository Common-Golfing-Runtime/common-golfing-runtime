@file:JvmName("IoOps")

package io.github.cgr.glib.impls

import kotlin.jvm.JvmName

/**
 * Prints the string representation of the given object to the standard output stream.
 */
@JvmName("print")
fun gPrint(arg: Any?) {
    print(asString(arg))
}

/**
 * Prints the string representation of the given object to the standard output stream, followed by a line separator.
 */
@JvmName("println")
fun gPrintln(arg: Any?) {
    println(asString(arg))
}
