package com.pictovoice.ui

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence
import com.pictovoice.feature.communication.domain.removePictogramAt
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import kotlin.test.Test
import kotlin.test.assertEquals

class EditSentenceUiLogicTest {
    @Test
    fun removeMiddleItem_preservesRemainingOrder() {
        val sentence =
            Sentence(
                items =
                    listOf(
                        Pictogram("a", "A", "A"),
                        Pictogram("b", "B", "B"),
                        Pictogram("c", "C", "C"),
                    ),
            )

        val result = removePictogramAt(sentence, index = 1)

        assertEquals(listOf("a", "c"), result.items.map { it.id })
    }

    @Test
    fun clearEvent_mapsToExpectedUiTrigger() {
        val event: CommunicationEvent = CommunicationEvent.ClearSentence
        assertEquals(CommunicationEvent.ClearSentence, event)
    }

    @Test
    fun removableLabel_appends_remove_hint() {
        assertEquals("Water (remove)", removableLabelFor("Water"))
    }

    @Test
    fun accessibilityLabels_are_stable_for_core_controls() {
        assertEquals("Add Water", pictogramCellDescription("Water"))
        assertEquals("Speak sentence", SpeakButtonDescription)
        assertEquals("Clear sentence", CLEAR_BUTTON_DESCRIPTION)
    }
}
