package io.github.cgr.app.objects

import io.github.cgr.app.io.Unpacker

interface ConstantLabel {

    companion object {
        /**
         * Gets the constant label with the given [label] name
         */
        operator fun get(label: String): ConstantLabel =
            constantLabels[label] ?: throw IllegalArgumentException("Unknown constant label: $label")
    }

    /**
     * The label name
     */
    val name: String

    /**
     * Unpacks the constant value of this label from the given [unpacker]
     */
    fun getConstant(unpacker: Unpacker, loaded: List<Any?>): Any?

    /**
     * Registers the constant label in the global constant label registry.
     */
    fun register() {
        constantLabels[name] = this
    }

    /**
     * Unregisters the constant label from the global constant label registry.
     */
    fun unregister() {
        constantLabels.remove(name)
    }
}

private val constantLabels = mutableMapOf<String, ConstantLabel>()