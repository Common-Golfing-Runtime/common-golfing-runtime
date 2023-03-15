package io.github.cgr.api.data

import Decimal

actual typealias Dec = Decimal

actual fun newBigDecimal(value: String): Dec {
    Decimal.precision = 128
    return Decimal(value)
}

actual operator fun Dec.plus(other: Dec): Dec = this.add(other)
actual operator fun Dec.minus(other: Dec): Dec = this.sub(other)
actual operator fun Dec.times(other: Dec): Dec = this.mul(other)
actual operator fun Dec.div(other: Dec): Dec = this.div(other)