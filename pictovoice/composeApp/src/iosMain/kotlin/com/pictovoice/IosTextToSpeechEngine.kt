package com.pictovoice

import com.pictovoice.feature.communication.domain.TextToSpeechEngine
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

class IosTextToSpeechEngine : TextToSpeechEngine {
    private val synthesizer = AVSpeechSynthesizer()

    override suspend fun speak(text: String) {
        if (text.isBlank()) return

        val utterance = AVSpeechUtterance(string = text)
        AVSpeechSynthesisVoice.voiceWithLanguage("en-US")?.let { voice ->
            utterance.voice = voice
        }
        synthesizer.speakUtterance(utterance)
    }
}
