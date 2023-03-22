package io.github.cgr.app.io

import decodeMulti
import encode
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get

actual class Packer {

    private val buffer = mutableListOf<Byte>()

    actual fun pack(value: Int) = packValue(value)

    actual fun pack(value: String) = packValue(value)

    actual fun pack(value: Boolean) = packValue(value)

    actual fun pack(value: Double) = packValue(value)

    actual fun pack(value: Nothing?) = packValue(value)

    actual fun toByteArray(): ByteArray {
        return buffer.toByteArray()
    }

    private fun packValue(value: Any?) {
        val encoded = encode(value)
        for (i in 0 until encoded.length) {
            buffer.add(encoded[i])
        }
    }
}

private fun decode(buffer: Uint8Array): Array<Any?> {
    val decoded = decodeMulti(buffer)
    return js("Array.from(decoded)") as Array<Any?>
}

actual class Unpacker actual constructor(buffer: ByteArray) {

    private val decoded = decode(Uint8Array(buffer.toTypedArray()))
    private var index = 0

    actual fun unpackInt(): Int = next()

    actual fun unpackString(): String = next()

    actual fun unpackBoolean(): Boolean = next()

    actual fun unpackDouble(): Double = next()

    actual fun tryUnpackNull(): Boolean {
        if (decoded[index] == null) {
            index++
            return true
        }
        return false
    }

    private inline fun <reified T> next(): T {
        val value = decoded[index++]
        if (value !is T) {
            throw IllegalStateException(
                "Expected ${T::class.simpleName} but got ${if (value == null) "null" else value::class.simpleName}"
            )
        }
        return value
    }
}