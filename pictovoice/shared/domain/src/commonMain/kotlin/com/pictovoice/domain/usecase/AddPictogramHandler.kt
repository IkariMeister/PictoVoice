package com.pictovoice.domain.usecase

import com.pictovoice.domain.model.Sentence
import com.pictovoice.domain.repo.VocabularyRepository

class AddPictogramHandler(
    private val vocabularyRepository: VocabularyRepository,
) {
    suspend operator fun invoke(pictogramId: String, sentence: Sentence): Sentence {
        val exists = vocabularyRepository.getPictogram(pictogramId) != null
        if (!exists) return sentence
        return sentence.copy(
            items = sentence.items + pictogramId,
            revision = sentence.revision + 1,
        )
    }
}
