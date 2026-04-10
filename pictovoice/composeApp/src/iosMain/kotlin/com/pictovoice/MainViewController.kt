package com.pictovoice

import androidx.compose.ui.window.ComposeUIViewController
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.pictovoice.data.local.LocalVocabularyDataSource
import com.pictovoice.data.repo.DefaultVocabularyRepository
import com.pictovoice.data.seed.SeedVocabularyLoader
import com.pictovoice.db.PictoVoiceDb
import com.pictovoice.domain.dispatcher.DefaultCommandHandler
import com.pictovoice.domain.usecase.AddPictogramHandler
import com.pictovoice.domain.usecase.SpeakSentenceHandler
import com.pictovoice.presentation.communication.CommunicationViewModel
import com.pictovoice.bootstrap.IosSentry
import com.pictovoice.tts.IosTextToSpeech
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    IosSentry.init("")
    val driver = NativeSqliteDriver(PictoVoiceDb.Schema, "pictovoice.db")
    val local = LocalVocabularyDataSource(driver)
    val seed =
        """
 {"pictograms":[
        {"id":"p1","label":"Yes","spoken_text":"Yes","sort_order":0},
        {"id":"p2","label":"No","spoken_text":"No","sort_order":1},
        {"id":"p3","label":"Water","spoken_text":"Water","sort_order":2},
        {"id":"p4","label":"Help","spoken_text":"Help","sort_order":3}
        ]}
        """.trimIndent()
    SeedVocabularyLoader.loadIfNeeded(local, seed)
    val repo = DefaultVocabularyRepository(local)
    val tts = IosTextToSpeech()
    val dispatcher =
        DefaultCommandHandler(
            AddPictogramHandler(repo),
            SpeakSentenceHandler(repo, tts),
        )
    val vm = CommunicationViewModel(dispatcher, repo)
    return ComposeUIViewController { App(vm) }
}
