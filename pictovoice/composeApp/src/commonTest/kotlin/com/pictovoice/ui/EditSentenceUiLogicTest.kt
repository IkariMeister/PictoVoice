package com.pictovoice.ui

import kotlin.test.Test
import kotlin.test.assertEquals

class EditSentenceUiLogicTest {
    @Test
    fun accessibilityLabels_areStable_forCoreControls() {
        assertEquals("Add Water", pictogramCellDescription("Water"))
        assertEquals("Speak sentence", SpeakButtonDescription)
        assertEquals("Clear sentence", CLEAR_BUTTON_DESCRIPTION)
    }
}
