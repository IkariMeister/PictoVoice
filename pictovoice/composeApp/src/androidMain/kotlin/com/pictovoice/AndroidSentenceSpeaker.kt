package com.pictovoice

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class AndroidSentenceSpeaker(
    context: Context,
) : TextToSpeech.OnInitListener {
    private var ready = false
    private val textToSpeech = TextToSpeech(context.applicationContext, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
            ready = true
        }
    }

    fun speak(text: String) {
        if (!ready || text.isBlank()) return
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "pictovoice_sentence")
    }

    fun shutdown() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
