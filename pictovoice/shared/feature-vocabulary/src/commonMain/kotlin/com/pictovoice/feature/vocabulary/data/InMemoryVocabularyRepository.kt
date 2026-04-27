package com.pictovoice.feature.vocabulary.data

import com.pictovoice.core.model.Pictogram
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository

class InMemoryVocabularyRepository : VocabularyRepository {
    override suspend fun listPictograms(): List<Pictogram> =
        listOf(
            Pictogram("yes", "Yes", "Yes"),
            Pictogram("no", "No", "No"),
            Pictogram("water", "Water", "Water"),
            Pictogram("help", "Help", "Help"),
        )
}
