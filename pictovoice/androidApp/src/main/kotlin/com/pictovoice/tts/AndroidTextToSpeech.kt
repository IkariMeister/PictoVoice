package com.pictovoice.tts

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech as AndroidEngine
import android.speech.tts.UtteranceProgressListener
import com.pictovoice.domain.tts.TextToSpeech
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class AndroidTextToSpeech(
    context: Context,
) : TextToSpeech {
    private val appContext = context.applicationContext
    private var engine: AndroidEngine? = null

    private suspend fun ensureEngine(): AndroidEngine {
        engine?.let { return it }
        return withContext(Dispatchers.Main.immediate) {
            suspendCancellableCoroutine { cont ->
                lateinit var created: AndroidEngine
                created =
                    AndroidEngine(appContext) { status ->
                        if (status == AndroidEngine.SUCCESS) {
                            engine = created
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                created.language = Locale.getDefault()
                            }
                            cont.resume(created)
                        } else {
                            cont.resumeWithException(IllegalStateException("TTS init failed: $status"))
                        }
                    }
            }
        }
    }

    override suspend fun speak(text: String) {
        val e = ensureEngine()
        withContext(Dispatchers.Main.immediate) {
            suspendCancellableCoroutine { cont ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    e.setOnUtteranceProgressListener(
                        object : UtteranceProgressListener() {
                            override fun onStart(utteranceId: String?) {}

                            override fun onDone(utteranceId: String?) {
                                cont.resume(Unit)
                            }

                            @Deprecated("Deprecated in Java")
                            override fun onError(utteranceId: String?) {
                                cont.resumeWithException(IllegalStateException("TTS error"))
                            }

                            override fun onError(utteranceId: String?, errorCode: Int) {
                                cont.resumeWithException(IllegalStateException("TTS error: $errorCode"))
                            }
                        },
                    )
                    e.speak(text, AndroidEngine.QUEUE_FLUSH, null, "pictovoice_utt")
                } else {
                    @Suppress("DEPRECATION")
                    e.speak(text, AndroidEngine.QUEUE_FLUSH, null)
                    cont.resume(Unit)
                }
            }
        }
    }

    override fun stop() {
        engine?.stop()
    }
}
