package com.pictovoice.domain.usecase

import com.pictovoice.domain.fixtures.DomainFixtures
import com.pictovoice.domain.model.Pictogram
import com.pictovoice.domain.repo.VocabularyRepository
import com.pictovoice.domain.tts.TextToSpeech
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class SpeakSentenceHandlerTest {
    @Test
    fun addPictogram_appends_whenPresentInCatalog() = runTest {
        val repo =
            object : VocabularyRepository {
                override suspend fun getPictogram(id: String): Pictogram? =
                    DomainFixtures.samplePictograms.find { it.id == id }

                override suspend fun listPictogramsForGrid(): List<Pictogram> =
                    DomainFixtures.samplePictograms
            }
        val handler = AddPictogramHandler(repo)
        val next = handler("a", DomainFixtures.sentence())
        assertEquals(listOf("a"), next.items)
    }

    @Test
    fun speakSentence_invokesTtsWithJoinedSpokenText() = runTest {
        val spoken = mutableListOf<String>()
        val tts =
            object : TextToSpeech {
                override suspend fun speak(text: String) {
                    spoken += text
                }

                override fun stop() {}
            }
        val repo =
            object : VocabularyRepository {
                override suspend fun getPictogram(id: String): Pictogram? =
                    DomainFixtures.samplePictograms.find { it.id == id }

                override suspend fun listPictogramsForGrid(): List<Pictogram> =
                    DomainFixtures.samplePictograms
            }
        val handler = SpeakSentenceHandler(repo, tts)
        handler(DomainFixtures.sentence("a", "b"))
        assertEquals(listOf("Letter A Letter B"), spoken)
    }

    @Test
    fun speakSentence_emptySentence_noTts() = runTest {
        val spoken = mutableListOf<String>()
        val tts =
            object : TextToSpeech {
                override suspend fun speak(text: String) {
                    spoken += text
                }

                override fun stop() {}
            }
        val repo =
            object : VocabularyRepository {
                override suspend fun getPictogram(id: String): Pictogram? = null

                override suspend fun listPictogramsForGrid(): List<Pictogram> = emptyList()
            }
        SpeakSentenceHandler(repo, tts)(DomainFixtures.sentence())
        assertTrue(spoken.isEmpty())
    }
}
