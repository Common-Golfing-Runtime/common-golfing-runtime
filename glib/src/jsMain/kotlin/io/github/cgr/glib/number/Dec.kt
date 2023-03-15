@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.github.cgr.glib.number

import Decimal

actual typealias Dec = Decimal

private var isInitialized = false

actual fun newDec(value: String): Dec {
    if (!isInitialized) {
        Decimal.precision = 128
        isInitialized = true
    }
    return Decimal(value)
}

actual operator fun Dec.plus(other: Dec): Dec = this.add(other)

actual operator fun Dec.minus(other: Dec): Dec = this.sub(other)

actual operator fun Dec.times(other: Dec): Dec = this.mul(other)

actual operator fun Dec.div(other: Dec): Dec = this.div(other)

actual operator fun Dec.unaryMinus(): Dec = this.neg()

actual fun Dec.power(exponent: Int): Dec = this.pow(exponent)

actual fun Dec.power(exponent: Dec): Dec = this.pow(exponent)

actual fun Dec.sqrt(): Dec = this.sqrt()

actual fun Dec.sin(): Dec = this.sin()

actual fun Dec.asin(): Dec = this.inverseSine()

actual fun Dec.cos(): Dec = this.cos()

actual fun Dec.acos(): Dec = this.inverseCosine()

actual fun Dec.tan(): Dec = this.tan()

actual fun Dec.atan(): Dec = this.inverseTangent()