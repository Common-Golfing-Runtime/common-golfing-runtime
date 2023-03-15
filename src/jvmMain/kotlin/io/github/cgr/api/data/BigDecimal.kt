package io.github.cgr.api.data

actual typealias BigDecimal = java.math.BigDecimal

actual fun newBigDecimal(value: String): BigDecimal {
    return BigDecimal(value)
}

actual operator fun BigDecimal.plus(other: BigDecimal): BigDecimal = this.add(other)
actual operator fun BigDecimal.minus(other: BigDecimal): BigDecimal = this.subtract(other)
actual operator fun BigDecimal.times(other: BigDecimal): BigDecimal = this.multiply(other)
actual operator fun BigDecimal.div(other: BigDecimal): BigDecimal = this.divide(other)