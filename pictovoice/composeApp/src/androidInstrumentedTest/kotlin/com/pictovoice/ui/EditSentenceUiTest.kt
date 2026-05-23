package com.pictovoice.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pictovoice.core.model.Pictogram
import org.junit.Rule
import org.junit.Test

class EditSentenceUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun tapping_sentence_item_removes_that_item() {
        composeRule.setContent {
            var sentence by
                remember {
                    mutableStateOf(
                        listOf(
                            Pictogram("yes", "Yes", "Yes"),
                            Pictogram("water", "Water", "Water"),
                        ),
                    )
                }

            SentenceBuilderBar(
                sentencePictograms = sentence,
                onPictogramTapped = { index ->
                    sentence = sentence.toMutableList().also { it.removeAt(index) }
                },
            )
        }

        composeRule.onNodeWithText("Yes (remove)").performClick()
        check(composeRule.onAllNodesWithText("Yes (remove)").fetchSemanticsNodes().isEmpty())
        check(composeRule.onAllNodesWithText("Water (remove)").fetchSemanticsNodes().isNotEmpty())
    }

    @Test
    fun tapping_clear_button_empties_sentence_strip() {
        composeRule.setContent {
            var sentence by
                remember {
                    mutableStateOf(
                        listOf(
                            Pictogram("yes", "Yes", "Yes"),
                            Pictogram("water", "Water", "Water"),
                        ),
                    )
                }

            Column {
                SentenceBuilderBar(
                    sentencePictograms = sentence,
                    onPictogramTapped = { index ->
                        sentence = sentence.toMutableList().also { it.removeAt(index) }
                    },
                )
                ClearSentenceButton(
                    enabled = sentence.isNotEmpty(),
                    onClick = { sentence = emptyList() },
                )
            }
        }

        composeRule.onNodeWithContentDescription(CLEAR_BUTTON_DESCRIPTION).performClick()
        check(composeRule.onAllNodesWithText("Yes (remove)").fetchSemanticsNodes().isEmpty())
        check(composeRule.onAllNodesWithText("Water (remove)").fetchSemanticsNodes().isEmpty())
        check(composeRule.onAllNodesWithText("…").fetchSemanticsNodes().isNotEmpty())
    }

    @Test
    fun speak_and_clear_controls_expose_accessibility_descriptions() {
        composeRule.setContent {
            Column {
                SpeakButton(
                    enabled = true,
                    isSpeaking = false,
                    onClick = {},
                )
                ClearSentenceButton(
                    enabled = true,
                    onClick = {},
                )
            }
        }

        composeRule.onNodeWithContentDescription(SpeakButtonDescription).performClick()
        composeRule.onNodeWithContentDescription(CLEAR_BUTTON_DESCRIPTION).performClick()
    }
}
