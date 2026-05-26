package com.pictovoice.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pictovoice.App
import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.telemetry.Telemetry
import com.pictovoice.feature.communication.domain.TextToSpeechEngine
import com.pictovoice.feature.communication.presentation.CommunicationViewModel
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommunicationScreenFlowTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selecting_two_pictograms_updates_sentence_and_speaks_full_sequence() {
        val spoken = mutableListOf<String>()
        val viewModel =
            CommunicationViewModel(
                vocabularyRepository = testVocabularyRepository(),
                textToSpeechEngine =
                    object : TextToSpeechEngine {
                        override suspend fun speak(text: String) {
                            spoken += text
                        }
                    },
                telemetry = object : Telemetry {
                    override fun event(name: String, attributes: Map<String, String>) = Unit
                },
                dispatcher = UnconfinedTestDispatcher(),
            )

        composeRule.setContent { App(viewModel = viewModel) }

        composeRule.onNodeWithContentDescription("Add Yes").performClick()
        composeRule.onNodeWithContentDescription("Add Water").performClick()

        check(composeRule.onAllNodesWithText("Yes (remove)").fetchSemanticsNodes().isNotEmpty())
        check(composeRule.onAllNodesWithText("Water (remove)").fetchSemanticsNodes().isNotEmpty())
        composeRule.onNodeWithContentDescription("Prediction Water").performClick()
        check(composeRule.onAllNodesWithText("Water (remove)").fetchSemanticsNodes().size >= 2)

        composeRule.onNodeWithContentDescription(SpeakButtonDescription).performClick()

        assertEquals(listOf("Yes Water Water"), spoken)
    }

    private fun testVocabularyRepository() =
        object : VocabularyRepository {
            private val pictograms =
                listOf(
                    Pictogram("yes", "Yes", "Yes"),
                    Pictogram("water", "Water", "Water"),
                    Pictogram("help", "Help", "Help"),
                )

            override suspend fun listPictograms(): List<Pictogram> = pictograms

            override suspend fun getPictogramById(id: String): Pictogram? = pictograms.find { it.id == id }
        }
}
