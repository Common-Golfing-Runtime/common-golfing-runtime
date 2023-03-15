package io.github.cgr.api.data

import ch.obermuhlner.math.big.BigComplex
import java.math.MathContext

actual typealias Complex = BigComplex

actual fun newComplex(real: Dec, imaginary: Dec): Complex {
    return BigComplex.valueOf(real, imaginary)
}

actual operator fun Complex.plus(other: Complex): Complex = this.add(other)
actual operator fun Complex.minus(other: Complex): Complex = this.subtract(other)
actual operator fun Complex.times(other: Complex): Complex = this.multiply(other)
actual operator fun Complex.div(other: Complex): Complex = this.divide(other, MathContext.DECIMAL128)