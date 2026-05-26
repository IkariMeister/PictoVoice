package com.pictovoice.feature.communication.domain

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence

class OnDevicePredictionEngine {
    operator fun invoke(
        sentence: Sentence,
        vocabulary: List<Pictogram>,
    ): List<Pictogram> {
        if (vocabulary.isEmpty()) return emptyList()

        val recentSelections =
            sentence.items
                .asReversed()
                .distinctBy { it.id }

        val remainingVocabulary =
            vocabulary.filterNot { vocabularyItem ->
                recentSelections.any { it.id == vocabularyItem.id }
            }

        return recentSelections + remainingVocabulary
    }
}
