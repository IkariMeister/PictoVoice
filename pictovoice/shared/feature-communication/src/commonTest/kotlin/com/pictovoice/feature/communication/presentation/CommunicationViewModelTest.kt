package com.pictovoice.feature.communication.presentation

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.telemetry.Telemetry
import com.pictovoice.feature.communication.domain.TextToSpeechEngine
import com.pictovoice.feature.vocabulary.data.network.NetworkMonitor
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout

class CommunicationViewModelTest {
    @Test
    fun selectPictogram_updates_sentence() = runTest {
        val repo =
            object : VocabularyRepository {
                override suspend fun listPictograms(): List<Pictogram> =
                    listOf(Pictogram("yes", "Yes", "Yes"))

                override suspend fun getPictogramById(id: String): Pictogram? =
                    Pictogram("yes", "Yes", "Yes")
            }
        val vm =
            CommunicationViewModel(
                vocabularyRepository = repo,
                telemetry = object : Telemetry { override fun event(name: String, attributes: Map<String, String>) = Unit },
            )
        delay(10)
        vm.onEvent(CommunicationEvent.SelectPictogram(Pictogram("yes", "Yes", "Yes")))
        assertEquals(1, vm.state.value.sentence.items.size)
    }

    @Test
    fun speakTapped_withEmptySentence_emitsEffect() = runTest {
        val vm =
            CommunicationViewModel(
                vocabularyRepository =
                    object : VocabularyRepository {
                        override suspend fun listPictograms(): List<Pictogram> = emptyList()
                        override suspend fun getPictogramById(id: String): Pictogram? = null
                    },
                telemetry = object : Telemetry { override fun event(name: String, attributes: Map<String, String>) = Unit },
            )

        vm.onEvent(CommunicationEvent.SpeakTapped)

        val effect = withTimeout(1_000) { vm.effects.first() }
        assertEquals(CommunicationEffect.EmptySentenceIgnored, effect)
    }

    @Test
    fun speakTapped_withSentence_invokesTtsAndResetsSpeakingFlag() = runTest {
        var spokenText: String? = null
        val tts =
            object : TextToSpeechEngine {
                override suspend fun speak(text: String) {
                    spokenText = text
                }
            }
        val vm =
            CommunicationViewModel(
                vocabularyRepository =
                    object : VocabularyRepository {
                        override suspend fun listPictograms(): List<Pictogram> =
                            listOf(Pictogram("yes", "Yes", "Yes"))

                        override suspend fun getPictogramById(id: String): Pictogram? =
                            Pictogram("yes", "Yes", "Yes")
                    },
                textToSpeechEngine = tts,
                telemetry = object : Telemetry { override fun event(name: String, attributes: Map<String, String>) = Unit },
            )

        vm.onEvent(CommunicationEvent.SelectPictogram(Pictogram("yes", "Yes", "Yes")))
        vm.onEvent(CommunicationEvent.SpeakTapped)

        delay(50)
        assertEquals("Yes", spokenText)
        assertFalse(vm.state.value.isSpeaking)
        assertTrue(vm.state.value.sentence.items.isNotEmpty())
    }

    @Test
    fun syncRequested_offline_emitsSkippedOfflineEffect() = runTest {
        val vm =
            CommunicationViewModel(
                vocabularyRepository =
                    object : VocabularyRepository {
                        override suspend fun listPictograms(): List<Pictogram> = emptyList()
                        override suspend fun getPictogramById(id: String): Pictogram? = null
                    },
                telemetry = object : Telemetry { override fun event(name: String, attributes: Map<String, String>) = Unit },
                networkMonitor = object : NetworkMonitor {
                    override fun isOnline(): Boolean = false
                },
            )

        vm.onEvent(CommunicationEvent.SyncRequested)

        val effect = withTimeout(1_000) { vm.effects.first() }
        assertEquals(CommunicationEffect.SyncSkippedOffline, effect)
    }

    @Test
    fun syncRequested_online_emitsCompletedEffect() = runTest {
        val vm =
            CommunicationViewModel(
                vocabularyRepository =
                    object : VocabularyRepository {
                        override suspend fun listPictograms(): List<Pictogram> =
                            listOf(Pictogram("yes", "Yes", "Yes"))

                        override suspend fun getPictogramById(id: String): Pictogram? =
                            Pictogram("yes", "Yes", "Yes")
                    },
                telemetry = object : Telemetry { override fun event(name: String, attributes: Map<String, String>) = Unit },
                networkMonitor = object : NetworkMonitor {
                    override fun isOnline(): Boolean = true
                },
            )

        vm.onEvent(CommunicationEvent.SyncRequested)

        val effect = withTimeout(1_000) { vm.effects.first() }
        assertEquals(CommunicationEffect.SyncCompleted, effect)
    }
}
