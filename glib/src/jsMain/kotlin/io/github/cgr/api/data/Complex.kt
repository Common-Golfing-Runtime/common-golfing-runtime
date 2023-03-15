package io.github.cgr.api.data

actual class Complex(actual val re: Dec, actual val im: Dec) {
    override fun toString(): String {
        return "$re + ${im}i"
    }
}

actual fun newComplex(real: Dec, imaginary: Dec): Complex {
    return Complex(real, imaginary)
}

actual operator fun Complex.plus(other: Complex): Complex {
    return Complex(re + other.re, im + other.im)
}

actual operator fun Complex.minus(other: Complex): Complex {
    return Complex(re - other.re, im - other.im)
}

actual operator fun Complex.times(other: Complex): Complex {
    return Complex(re * other.re - im * other.im, re * other.im + im * other.re)
}

actual operator fun Complex.div(other: Complex): Complex {
    val denominator = other.re * other.re + other.im * other.im
    return Complex((re * other.re + im * other.im) / denominator, (im * other.re - re * other.im) / denominator)
}