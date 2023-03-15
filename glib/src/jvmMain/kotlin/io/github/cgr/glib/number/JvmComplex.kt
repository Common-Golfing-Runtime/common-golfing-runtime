package io.github.cgr.glib.number

import ch.obermuhlner.math.big.BigComplex
import ch.obermuhlner.math.big.BigComplexMath
import java.math.MathContext

actual typealias Complex = BigComplex

actual fun newComplex(real: Dec, imaginary: Dec): Complex {
    return BigComplex.valueOf(real, imaginary)
}

private typealias BCM = BigComplexMath

actual operator fun Complex.plus(other: Complex): Complex = this.add(other)

actual operator fun Complex.minus(other: Complex): Complex = this.subtract(other)

actual operator fun Complex.times(other: Complex): Complex = this.multiply(other)

actual operator fun Complex.div(other: Complex): Complex = this.divide(other, MathContext.DECIMAL128)

actual fun Complex.abs(): Dec = BCM.abs(this, MathContext.DECIMAL128)

actual fun Complex.arg(): Dec = BCM.angle(this, MathContext.DECIMAL128)

actual fun Complex.power(exponent: Int): Complex = BCM.pow(this, exponent.toLong(), MathContext.DECIMAL128)

actual fun Complex.power(exponent: Dec): Complex = BCM.pow(this, exponent, MathContext.DECIMAL128)

actual fun Complex.power(exponent: Complex): Complex = BCM.pow(this, exponent, MathContext.DECIMAL128)