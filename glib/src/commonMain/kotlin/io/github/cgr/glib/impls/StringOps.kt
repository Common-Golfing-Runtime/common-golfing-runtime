@file:JvmName("StringOps")

package io.github.cgr.glib.impls

import io.github.cgr.glib.number.Complex
import io.github.cgr.glib.number.Dec
import io.github.cgr.glib.number.stringified
import kotlin.jvm.JvmName

/**
 * Returns the string representation of this object. Returns differently from [toString] for some types.
 */
fun asString(obj: Any?): String {
    if (obj == null) return "null"
    return when (obj) {
        is String -> obj
        is Dec -> obj.stringified()
        is Complex -> obj.stringified()
        else -> obj.toString()
    }
}