package io.github.cgr.api.data

import Decimal

actual typealias BigDecimal = Decimal

actual fun newBigDecimal(value: String): BigDecimal {
    Decimal.precision = 128
    return Decimal(value)
}

actual operator fun BigDecimal.plus(other: BigDecimal): BigDecimal = this.add(other)
actual operator fun BigDecimal.minus(other: BigDecimal): BigDecimal = this.sub(other)
actual operator fun BigDecimal.times(other: BigDecimal): BigDecimal = this.mul(other)
actual operator fun BigDecimal.div(other: BigDecimal): BigDecimal = this.div(other)