package com.pictovoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.pictovoice.feature.communication.presentation.CommunicationViewModel

class MainActivity : ComponentActivity() {
    private lateinit var textToSpeechEngine: AndroidTextToSpeechEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeechEngine = AndroidTextToSpeechEngine(this)
        enableEdgeToEdge()
        setContent {
            val viewModel = remember(textToSpeechEngine) {
                CommunicationViewModel(textToSpeechEngine = textToSpeechEngine)
            }
            App(viewModel = viewModel)
        }
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }
}
