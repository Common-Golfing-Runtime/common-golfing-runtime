package io.github.cgr.glib.number

import ch.obermuhlner.math.big.DefaultBigDecimalMath
import java.math.BigDecimal
import java.math.MathContext

actual typealias Dec = BigDecimal

private var isInitialized = false

actual fun newDec(value: String): Dec {
    if (!isInitialized) {
        DefaultBigDecimalMath.setDefaultMathContext(MathContext.DECIMAL128)
        isInitialized = true
    }
    return Dec(value)
}

actual fun Dec.stringified(): String = this.stripTrailingZeros().toPlainString()

private typealias BDM = DefaultBigDecimalMath

actual operator fun Dec.plus(other: Dec): Dec = this.add(other)

actual operator fun Dec.minus(other: Dec): Dec = this.subtract(other)

actual operator fun Dec.times(other: Dec): Dec = this.multiply(other)

actual operator fun Dec.div(other: Dec): Dec = this.divide(other)

actual operator fun Dec.unaryMinus(): Dec = this.negate()

actual operator fun Dec.compareTo(other: Dec): Int = this.compareTo(other)

actual fun Dec.power(exponent: Int): Dec = BDM.pow(this, exponent.toLong())

actual fun Dec.power(exponent: Dec): Dec = BDM.pow(this, exponent)

actual fun Dec.sqrt(): Dec = BDM.sqrt(this)

actual fun Dec.log(): Dec = BDM.log(this)

actual fun Dec.exp(): Dec = BDM.exp(this)

actual fun Dec.sin(): Dec = BDM.sin(this)

actual fun Dec.asin(): Dec = BDM.asin(this)

actual fun Dec.cos(): Dec = BDM.cos(this)

actual fun Dec.acos(): Dec = BDM.acos(this)

actual fun Dec.tan(): Dec = BDM.tan(this)

actual fun Dec.atan(): Dec = BDM.atan(this)
