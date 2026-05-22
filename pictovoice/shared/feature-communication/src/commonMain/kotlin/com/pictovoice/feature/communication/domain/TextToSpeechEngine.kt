package com.pictovoice.feature.communication.domain

interface TextToSpeechEngine {
    suspend fun speak(text: String)
}

object NoopTextToSpeechEngine : TextToSpeechEngine {
    override suspend fun speak(text: String) = Unit
}
