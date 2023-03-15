package io.github.cgr.api.data

expect class Complex {
    val re: Dec
    val im: Dec
}

expect fun newComplex(real: Dec, imaginary: Dec): Complex

expect operator fun Complex.plus(other: Complex): Complex
expect operator fun Complex.minus(other: Complex): Complex
expect operator fun Complex.times(other: Complex): Complex
expect operator fun Complex.div(other: Complex): Complex