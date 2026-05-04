package com.pictovoice.ui

import com.pictovoice.feature.communication.presentation.CommunicationEffect
import kotlin.test.Test
import kotlin.test.assertEquals

class CommunicationScreenFeedbackTest {
    @Test
    fun message_forEmptySentenceIgnored_isExpected() {
        assertEquals("Sentence is empty", feedbackMessageFor(CommunicationEffect.EmptySentenceIgnored))
    }

    @Test
    fun message_forSyncSkippedOffline_isExpected() {
        assertEquals("Offline: sync skipped", feedbackMessageFor(CommunicationEffect.SyncSkippedOffline))
    }

    @Test
    fun message_forSyncCompleted_isExpected() {
        assertEquals("Synced successfully", feedbackMessageFor(CommunicationEffect.SyncCompleted))
    }
}
