package com.pictovoice.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.down
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.moveTo
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.up
import com.pictovoice.core.model.Pictogram
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
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
                    if (index in sentence.indices) {
                        sentence = sentence.toMutableList().also { it.removeAt(index) }
                    }
                },
            )
        }

        composeRule.onNodeWithText("Yes (remove)").performClick()
        assertEquals(0, composeRule.onAllNodesWithText("Yes (remove)").fetchSemanticsNodes().size)
        assertEquals(1, composeRule.onAllNodesWithText("Water (remove)").fetchSemanticsNodes().size)
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
                    if (index in sentence.indices) {
                        sentence = sentence.toMutableList().also { it.removeAt(index) }
                    }
                },
            )
        }

        val yesNode = composeRule.onNodeWithText("Yes (remove)")
        yesNode.performClick()

        assertEquals(0, composeRule.onAllNodesWithText("Yes (remove)").fetchSemanticsNodes().size)
        assertEquals(1, composeRule.onAllNodesWithText("Water (remove)").fetchSemanticsNodes().size)
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
                        if (index in sentence.indices) {
                            sentence = sentence.toMutableList().also { it.removeAt(index) }
                        }
                    },
                )
                ClearSentenceButton(
                    enabled = sentence.isNotEmpty(),
                    onClick = { sentence = emptyList() },
                )
            }
        }

        composeRule.onNodeWithContentDescription(CLEAR_BUTTON_DESCRIPTION).performClick()
        assertEquals(0, composeRule.onAllNodesWithText("Yes (remove)").fetchSemanticsNodes().size)
        assertEquals(0, composeRule.onAllNodesWithText("Water (remove)").fetchSemanticsNodes().size)
        assertEquals(1, composeRule.onAllNodesWithText("…").fetchSemanticsNodes().size)
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
    fun pictogram_cell_pressed_state_toggles_during_touch_interaction() {
        val interactionSource = MutableInteractionSource()
        composeRule.setContent {
            PictogramCell(
                pictogram = Pictogram("yes", "Yes", "Yes"),
                onClick = {},
                interactionSource = interactionSource,
            )
        }

        val cellNode = composeRule.onNodeWithContentDescription(pictogramCellDescription("Yes"))
        cellNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, NotPressedStateDescription),
        )

        val press = PressInteraction.Press(Offset.Zero)
        runTest {
            interactionSource.emit(press)
        }
        composeRule.waitForIdle()
        cellNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, PressedStateDescription),
        )
        runTest {
            interactionSource.emit(PressInteraction.Release(press))
        }
        composeRule.waitForIdle()
        cellNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, NotPressedStateDescription),
        )
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

    @Test
    fun clearButton_pressed_state_toggles_during_touch_interaction() {
        composeRule.setContent {
            ClearSentenceButton(
                enabled = true,
                onClick = {},
            )
        }

        val clearButtonNode = composeRule.onNodeWithContentDescription(CLEAR_BUTTON_DESCRIPTION)
        clearButtonNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, NotPressedStateDescription),
        )

        clearButtonNode.performTouchInput {
            val center = center
            down(center)
        }
        clearButtonNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, PressedStateDescription),
        )

        clearButtonNode.performTouchInput {
            val center = center
            moveTo(center)
            up()
        }
        clearButtonNode.assert(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, NotPressedStateDescription),
        )
    }
}
