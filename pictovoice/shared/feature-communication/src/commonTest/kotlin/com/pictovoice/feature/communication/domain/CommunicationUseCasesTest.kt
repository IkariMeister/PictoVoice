package com.pictovoice.feature.communication.domain

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class CommunicationUseCasesTest {
    @Test
    fun addPictogram_appends_item() {
        val result = addPictogram(Sentence(), Pictogram("yes", "Yes", "Yes"))
        assertEquals(1, result.items.size)
        assertEquals("yes", result.items.first().id)
    }

    @Test
    fun clearSentence_empties_items() {
        val result = clearSentence()
        assertTrue(result.items.isEmpty())
    }

    @Test
    fun speakSentence_joins_and_speaks_spoken_text() = runTest {
        val spoken = mutableListOf<String>()
        val tts =
            object : TextToSpeechEngine {
                override suspend fun speak(text: String) {
                    spoken += text
                }
            }
        val repo =
            object : VocabularyRepository {
                private val data =
                    listOf(
                        Pictogram("yes", "Yes", "Yes"),
                        Pictogram("water", "Water", "Water"),
                    )

                override suspend fun listPictograms(): List<Pictogram> = data
                override suspend fun getPictogramById(id: String): Pictogram? = data.find { it.id == id }
            }

        val sentence = Sentence(items = listOf(Pictogram("yes", "Yes", "Yes"), Pictogram("water", "Water", "Water")))
        speakSentence(sentence, repo, tts)
        assertEquals(listOf("Yes Water"), spoken)
    }
}
