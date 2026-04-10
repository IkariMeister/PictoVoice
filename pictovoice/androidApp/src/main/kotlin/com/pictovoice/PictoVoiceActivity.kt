package com.pictovoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pictovoice.data.local.LocalVocabularyDataSource
import com.pictovoice.data.repo.DefaultVocabularyRepository
import com.pictovoice.data.seed.SeedVocabularyLoader
import com.pictovoice.db.PictoVoiceDb
import com.pictovoice.domain.dispatcher.DefaultCommandHandler
import com.pictovoice.domain.usecase.AddPictogramHandler
import com.pictovoice.domain.usecase.SpeakSentenceHandler
import com.pictovoice.presentation.communication.CommunicationViewModel
import com.pictovoice.tts.AndroidTextToSpeech

class PictoVoiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val driver = AndroidSqliteDriver(PictoVoiceDb.Schema, this, "pictovoice.db")
        val local = LocalVocabularyDataSource(driver)
        val seedJson =
            assets.open("seed/pictograms.json").bufferedReader().use { it.readText() }
        SeedVocabularyLoader.loadIfNeeded(local, seedJson)
        val repo = DefaultVocabularyRepository(local)
        val tts = AndroidTextToSpeech(this)
        val add = AddPictogramHandler(repo)
        val speak = SpeakSentenceHandler(repo, tts)
        val dispatcher = DefaultCommandHandler(add, speak)
        val vm = CommunicationViewModel(dispatcher, repo)
        setContent { App(vm) }
    }
}
