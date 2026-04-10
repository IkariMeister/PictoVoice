package com.pictovoice.presentation.communication

import com.pictovoice.domain.command.DomainCommand
import com.pictovoice.domain.dispatcher.CommandHandler
import com.pictovoice.domain.model.Pictogram
import com.pictovoice.domain.model.Sentence
import com.pictovoice.domain.repo.VocabularyRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class CommunicationViewModelTest {
    private val pictograms =
        listOf(
            Pictogram("a", "A", "Letter A", null, null, 0),
            Pictogram("b", "B", "Letter B", null, null, 1),
        )

    @Test
    fun pictogramTapped_updatesSentence() = runTest {
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
        val vm = CommunicationViewModel(handler, repo, UnconfinedTestDispatcher())
        vm.state.first { it.pictograms.isNotEmpty() }
        vm.onEvent(CommunicationEvent.PictogramTapped("a"))
        assertEquals(listOf("a"), vm.state.value.sentence.items)
    }
}
