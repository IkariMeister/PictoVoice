package com.pictovoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    private lateinit var sentenceSpeaker: AndroidSentenceSpeaker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sentenceSpeaker = AndroidSentenceSpeaker(this)
        enableEdgeToEdge()
        setContent {
            App(onSpeakSentence = sentenceSpeaker::speak)
        }
    }

    override fun onDestroy() {
        sentenceSpeaker.shutdown()
        super.onDestroy()
    }
}
