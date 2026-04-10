package com.pictovoice.tts

import com.pictovoice.domain.tts.TextToSpeech

/** iOS TTS: no-op until AVFoundation interop is wired in the Xcode/K/N setup. */
class IosTextToSpeech : TextToSpeech {
    override suspend fun speak(text: String) {
    }

    override fun stop() {
    }
}
