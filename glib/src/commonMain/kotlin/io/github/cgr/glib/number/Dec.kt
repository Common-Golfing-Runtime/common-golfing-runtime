@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.github.cgr.glib.number

expect class Dec {
    fun abs(): Dec
}

expect fun newDec(value: String): Dec

fun Int.toDec(): Dec = newDec(this.toString())
fun Long.toDec(): Dec = newDec(this.toString())
fun Double.toDec(): Dec = newDec(this.toString())
fun String.toDec(): Dec = newDec(this)

val DEC_ZERO = newDec("0")
val DEC_ONE = newDec("1")
val DEC_TWO = newDec("2")
val DEC_TEN = newDec("10")

expect fun Dec.stringified(): String

expect operator fun Dec.plus(other: Dec): Dec
expect operator fun Dec.minus(other: Dec): Dec
expect operator fun Dec.times(other: Dec): Dec
expect operator fun Dec.div(other: Dec): Dec
expect operator fun Dec.unaryMinus(): Dec
expect operator fun Dec.compareTo(other: Dec): Int
expect fun Dec.power(exponent: Int): Dec
expect fun Dec.power(exponent: Dec): Dec
expect fun Dec.sqrt(): Dec
expect fun Dec.log(): Dec
expect fun Dec.exp(): Dec

expect fun Dec.sin(): Dec
expect fun Dec.asin(): Dec

expect fun Dec.cos(): Dec
expect fun Dec.acos(): Dec

expect fun Dec.tan(): Dec
expect fun Dec.atan(): Dec