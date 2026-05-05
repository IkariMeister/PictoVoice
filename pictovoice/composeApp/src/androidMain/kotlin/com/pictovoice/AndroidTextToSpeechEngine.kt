package com.pictovoice

import android.content.Context
import android.speech.tts.TextToSpeech
import com.pictovoice.feature.communication.domain.TextToSpeechEngine
import java.util.Locale
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeout

class AndroidTextToSpeechEngine(
    context: Context,
) : TextToSpeechEngine, TextToSpeech.OnInitListener {
    private val initResult = CompletableDeferred<Boolean>()
    private val textToSpeech = TextToSpeech(context.applicationContext, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
            initResult.complete(true)
        } else {
            initResult.complete(false)
        }
    }

    override suspend fun speak(text: String) {
        if (text.isBlank()) return
        val ok = runCatching { withTimeout(10_000L) { initResult.await() } }.getOrDefault(false)
        if (!ok) return
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "pictovoice_sentence")
    }

    fun shutdown() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
