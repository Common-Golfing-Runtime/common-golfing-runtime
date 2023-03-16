package io.github.cgr.internal

import org.msgpack.core.MessagePack

actual class Packer {

    private val packer = MessagePack.newDefaultBufferPacker()

    actual fun pack(value: Int) {
        packer.packInt(value)
    }

    actual fun pack(value: String) {
        packer.packString(value)
    }

    actual fun pack(value: Boolean) {
        packer.packBoolean(value)
    }

    actual fun pack(value: Double) {
        packer.packDouble(value)
    }

    actual fun pack(value: Nothing?) {
        packer.packNil()
    }

    actual fun toByteArray(): ByteArray {
        return packer.toByteArray()
    }
}

actual class Unpacker actual constructor(buffer: ByteArray) {

    private val unpacker = MessagePack.newDefaultUnpacker(buffer)

    actual fun unpackInt(): Int = unpacker.unpackInt()

    actual fun unpackString(): String = unpacker.unpackString()

    actual fun unpackBoolean(): Boolean = unpacker.unpackBoolean()

    actual fun unpackDouble(): Double = unpacker.unpackDouble()

    actual fun tryUnpackNull(): Boolean = unpacker.tryUnpackNil()
}