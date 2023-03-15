import org.khronos.webgl.Uint8Array

@JsNonModule
@JsModule("@msgpack/msgpack")
internal external fun encode(value: Any?, options: dynamic = definedExternally): Uint8Array

@JsNonModule
@JsModule("@msgpack/msgpack")
internal external fun decodeMulti(buffer: Uint8Array, options: dynamic = definedExternally): dynamic