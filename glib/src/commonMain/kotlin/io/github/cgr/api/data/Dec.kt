package io.github.cgr.api.data

expect class Dec {
    fun abs(): Dec
}

expect fun newBigDecimal(value: String): Dec

expect operator fun Dec.plus(other: Dec): Dec
expect operator fun Dec.minus(other: Dec): Dec
expect operator fun Dec.times(other: Dec): Dec
expect operator fun Dec.div(other: Dec): Dec