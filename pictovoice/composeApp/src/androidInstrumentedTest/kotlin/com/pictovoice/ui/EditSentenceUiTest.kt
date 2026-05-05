package com.pictovoice.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.down
import androidx.compose.ui.test.moveTo
import androidx.compose.ui.test.up
import androidx.compose.ui.test.performClick
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.semantics.SemanticsProperties
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

        composeRule.onNodeWithText("Yes (remove)").assertExists().performClick()
        composeRule.onNodeWithText("Yes (remove)").assertDoesNotExist()
        composeRule.onNodeWithText("Water (remove)").assertExists()
    }

    @Test
    fun swiping_sentence_item_left_removes_that_item() {
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

        val yesNode = composeRule.onNodeWithText("Yes (remove)")
        yesNode.assertExists()
        yesNode.performTouchInput {
            val start = center
            down(start)
            moveTo(Offset(x = start.x - (size.width * 0.8f), y = start.y))
            up()
        }

        composeRule.onNodeWithText("Yes (remove)").assertDoesNotExist()
        composeRule.onNodeWithText("Water (remove)").assertExists()
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

        composeRule.onNodeWithText("Yes (remove)").assertExists()
        composeRule.onNodeWithContentDescription(CLEAR_BUTTON_DESCRIPTION).assertExists().performClick()
        composeRule.onNodeWithText("Yes (remove)").assertDoesNotExist()
        composeRule.onNodeWithText("Water (remove)").assertDoesNotExist()
        composeRule.onNodeWithText("…").assertExists()
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

        composeRule.onNodeWithContentDescription(SpeakButtonDescription).assertExists()
        composeRule.onNodeWithContentDescription(CLEAR_BUTTON_DESCRIPTION).assertExists()
    }

    @Test
    fun speakButton_pressed_state_toggles_during_touch_interaction() {
        composeRule.setContent {
            SpeakButton(
                enabled = true,
                isSpeaking = false,
                onClick = {},
            )
        }

        val speakButtonNode = composeRule.onNodeWithContentDescription(SpeakButtonDescription)
        speakButtonNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, NotPressedStateDescription),
        )

        speakButtonNode.performTouchInput {
            val center = center
            down(center)
        }
        speakButtonNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, PressedStateDescription),
        )

        speakButtonNode.performTouchInput {
            val center = center
            moveTo(center)
            up()
        }
        speakButtonNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, NotPressedStateDescription),
        )
    }
}
