package io.github.cgr.app.objects

import io.github.cgr.app.io.Unpacker
import io.github.cgr.glib.number.Dec
import io.github.cgr.glib.number.newComplex
import io.github.cgr.glib.number.newDec

enum class BaseLabel(private val unpacking: (Unpacker, List<Any?>) -> Any?) : ConstantLabel {
    INT(Unpacker::unpackInt),
    STRING(Unpacker::unpackString),
    BOOLEAN(Unpacker::unpackBoolean),
    DECIMAL({ up -> newDec(up.unpackString()) }),
    COMPLEX({ up, cp -> newComplex(cp[up.unpackInt()] as Dec, cp[up.unpackInt()] as Dec) }),
    NULL({ _, _ -> null })
    ;

    constructor(unpacking: (Unpacker) -> Any?) : this({ unpacker, _ -> unpacking(unpacker) })

    override fun getConstant(unpacker: Unpacker, loaded: List<Any?>): Any? = unpacking(unpacker, loaded)
}