package com.pictovoice.feature.communication.presentation

import com.pictovoice.core.model.Pictogram
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CommunicationViewModelTest {
    @Test
    fun `loads pictograms on init from vocabulary repository`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val scope = CoroutineScope(SupervisorJob() + dispatcher)
            val expected =
                listOf(
                    Pictogram("yes", "Yes", "Yes"),
                    Pictogram("help", "Help", "Help"),
                )
            val repository = FakeVocabularyRepository(expected)

            val viewModel = CommunicationViewModel(vocabularyRepository = repository, coroutineScope = scope)

            advanceUntilIdle()

            assertEquals(expected, viewModel.state.value.pictograms)
            viewModel.clear()
        }

    @Test
    fun `select pictogram appends to sentence and clear resets sentence`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val scope = CoroutineScope(SupervisorJob() + dispatcher)
            val pictogram = Pictogram("water", "Water", "Water")
            val viewModel =
                CommunicationViewModel(
                    vocabularyRepository = FakeVocabularyRepository(listOf(pictogram)),
                    coroutineScope = scope,
                )

            advanceUntilIdle()
            viewModel.onEvent(CommunicationEvent.SelectPictogram(pictogram))

            assertEquals(listOf(pictogram), viewModel.state.value.sentence.items)

            viewModel.onEvent(CommunicationEvent.ClearSentence)

            assertEquals(emptyList(), viewModel.state.value.sentence.items)
            viewModel.clear()
        }

    @Test
    fun `speak sentence event triggers speak callback when sentence is not empty`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val scope = CoroutineScope(SupervisorJob() + dispatcher)
            val spokenSentences = mutableListOf<String>()
            val yes = Pictogram("yes", "Yes", "Yes")
            val water = Pictogram("water", "Water", "Water")
            val viewModel =
                CommunicationViewModel(
                    vocabularyRepository = FakeVocabularyRepository(listOf(yes, water)),
                    onSpeakSentence = { spokenSentences += it },
                    coroutineScope = scope,
                )

            advanceUntilIdle()
            viewModel.onEvent(CommunicationEvent.SelectPictogram(yes))
            viewModel.onEvent(CommunicationEvent.SelectPictogram(water))

            viewModel.onEvent(CommunicationEvent.SpeakSentence)

            assertEquals(listOf("Yes Water"), spokenSentences)
            assertEquals(listOf(yes, water), viewModel.state.value.sentence.items)
            viewModel.clear()
        }

    @Test
    fun `speak sentence event does not trigger callback when sentence is empty`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)
            val scope = CoroutineScope(SupervisorJob() + dispatcher)
            val spokenSentences = mutableListOf<String>()
            val viewModel =
                CommunicationViewModel(
                    vocabularyRepository = FakeVocabularyRepository(emptyList()),
                    onSpeakSentence = { spokenSentences += it },
                    coroutineScope = scope,
                )

            advanceUntilIdle()
            viewModel.onEvent(CommunicationEvent.SpeakSentence)

            assertTrue(spokenSentences.isEmpty())
            viewModel.clear()
        }

    private class FakeVocabularyRepository(
        private val pictograms: List<Pictogram>,
    ) : VocabularyRepository {
        override suspend fun listPictograms(): List<Pictogram> = pictograms
    }
}
