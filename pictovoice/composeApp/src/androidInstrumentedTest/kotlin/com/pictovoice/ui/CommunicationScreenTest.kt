package com.pictovoice.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pictovoice.App
import com.pictovoice.domain.command.DomainCommand
import com.pictovoice.domain.dispatcher.CommandHandler
import com.pictovoice.domain.model.Pictogram
import com.pictovoice.domain.model.Sentence
import com.pictovoice.domain.repo.VocabularyRepository
import com.pictovoice.presentation.communication.CommunicationViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommunicationScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun tapPictogram_sentenceStripUpdates() {
        val pictograms =
            listOf(
                Pictogram("a", "A", "Letter A", null, null, 0),
                Pictogram("b", "B", "Letter B", null, null, 1),
            )
        val repo =
            object : VocabularyRepository {
                override suspend fun getPictogram(id: String): Pictogram? =
                    pictograms.find { it.id == id }

                override suspend fun listPictogramsForGrid(): List<Pictogram> = pictograms
            }
        val handler =
            object : CommandHandler {
                override suspend fun handle(command: DomainCommand, sentence: Sentence): Sentence =
                    when (command) {
                        is DomainCommand.AddPictogram ->
                            sentence.copy(
                                items = sentence.items + command.id,
                                revision = sentence.revision + 1,
                            )
                        else -> sentence
                    }
            }
        val vm = CommunicationViewModel(handler, repo)
        rule.setContent { App(vm) }
        rule.waitForIdle()
        rule.onNodeWithText("A").performClick()
        rule.onNodeWithText("A").assertIsDisplayed()
        rule.onNodeWithText("Speak").assertIsDisplayed()
    }
}
