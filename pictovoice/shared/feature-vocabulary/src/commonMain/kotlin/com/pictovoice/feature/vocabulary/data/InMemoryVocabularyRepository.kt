package com.pictovoice.feature.vocabulary.data

import com.pictovoice.core.model.Pictogram
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository

class InMemoryVocabularyRepository : VocabularyRepository {
    private val pictograms =
        listOf(
            Pictogram("yes", "Yes", "Yes"),
            Pictogram("no", "No", "No"),
            Pictogram("water", "Water", "Water"),
            Pictogram("help", "Help", "Help"),
        )

    override suspend fun listPictograms(): List<Pictogram> = pictograms

    override suspend fun getPictogramById(id: String): Pictogram? = pictograms.find { it.id == id }
}
