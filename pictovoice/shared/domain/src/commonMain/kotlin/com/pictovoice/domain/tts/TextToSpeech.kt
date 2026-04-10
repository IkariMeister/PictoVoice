package com.pictovoice.domain.tts

/**
 * Platform TTS port. v1: single primary language; queue short utterances.
 */
interface TextToSpeech {
    suspend fun speak(text: String)
    fun stop()
}
