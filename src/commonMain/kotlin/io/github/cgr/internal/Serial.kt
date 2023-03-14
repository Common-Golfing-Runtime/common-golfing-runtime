package io.github.cgr.internal

expect class Packer {
    fun pack(value: Int)
    fun pack(value: String)
    fun pack(value: Boolean)
    fun pack(value: Double)
    fun pack(value: Nothing?)
    fun toByteArray(): ByteArray
}

expect class Unpacker(buffer: ByteArray) {
    fun unpackInt(): Int
    fun unpackString(): String
    fun unpackBoolean(): Boolean
    fun unpackDouble(): Double
    fun tryUnpackNull(): Boolean
}