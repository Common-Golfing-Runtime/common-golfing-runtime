package io.github.cgr.glib.number

expect class Complex {
    val re: Dec
    val im: Dec
}

expect fun newComplex(real: Dec, imaginary: Dec): Complex

expect operator fun Complex.plus(other: Complex): Complex
expect operator fun Complex.minus(other: Complex): Complex
expect operator fun Complex.times(other: Complex): Complex
expect operator fun Complex.div(other: Complex): Complex
expect fun Complex.abs(): Dec
expect fun Complex.arg(): Dec
expect fun Complex.power(exponent: Int): Complex
expect fun Complex.power(exponent: Dec): Complex
expect fun Complex.power(exponent: Complex): Complex