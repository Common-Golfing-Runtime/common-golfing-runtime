package io.github.cgr.glib.number

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

actual fun Complex.abs(): Dec {
    return (re * re + im * im).sqrt()
}

actual fun Complex.arg(): Dec {
    return (im / re).atan()
}

actual fun Complex.log(): Complex {
    val absLog = this.abs().log()
    val arg = this.arg()
    return newComplex(absLog, arg)
}

actual fun Complex.exp(): Complex {
    val realExp = this.re.exp()
    return newComplex(realExp * this.im.sin(), realExp * this.im.cos())
}

actual fun Complex.power(exponent: Int): Complex {
    val newAbs = this.abs().power(exponent)
    val newArg = this.arg() * exponent.toDec()
    return newComplex(newAbs * newArg.cos(), newAbs * newArg.sin())
}

actual fun Complex.power(exponent: Dec): Complex {
    val newAbs = abs().power(exponent)
    val newArg = arg() * exponent
    return newComplex(newAbs * newArg.cos(), newAbs * newArg.sin())
}

actual fun Complex.power(exponent: Complex): Complex = (exponent * this.log()).exp()