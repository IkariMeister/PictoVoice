package com.pictovoice.core.telemetry

interface Telemetry {
    fun event(name: String, attributes: Map<String, String> = emptyMap())
}

object NoopTelemetry : Telemetry {
    override fun event(name: String, attributes: Map<String, String>) = Unit
}
