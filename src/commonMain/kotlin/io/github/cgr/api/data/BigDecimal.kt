package io.github.cgr.api.data

expect class BigDecimal {
    fun abs(): BigDecimal
}

expect fun newBigDecimal(value: String): BigDecimal

expect operator fun BigDecimal.plus(other: BigDecimal): BigDecimal
expect operator fun BigDecimal.minus(other: BigDecimal): BigDecimal
expect operator fun BigDecimal.times(other: BigDecimal): BigDecimal
expect operator fun BigDecimal.div(other: BigDecimal): BigDecimal