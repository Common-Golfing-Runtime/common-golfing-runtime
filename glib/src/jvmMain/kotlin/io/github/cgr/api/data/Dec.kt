package io.github.cgr.api.data

import java.math.BigDecimal

actual typealias Dec = BigDecimal

actual fun newBigDecimal(value: String): Dec {
    return Dec(value)
}

actual operator fun Dec.plus(other: Dec): Dec = this.add(other)
actual operator fun Dec.minus(other: Dec): Dec = this.subtract(other)
actual operator fun Dec.times(other: Dec): Dec = this.multiply(other)
actual operator fun Dec.div(other: Dec): Dec = this.divide(other)