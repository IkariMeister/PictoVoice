package com.pictovoice.feature.communication.presentation

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.telemetry.Telemetry
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest

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
}
