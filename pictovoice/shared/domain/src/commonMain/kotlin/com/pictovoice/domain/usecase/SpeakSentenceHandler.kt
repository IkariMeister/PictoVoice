package com.pictovoice.domain.usecase

import com.pictovoice.domain.model.Sentence
import com.pictovoice.domain.repo.VocabularyRepository
import com.pictovoice.domain.tts.TextToSpeech

class SpeakSentenceHandler(
    private val vocabularyRepository: VocabularyRepository,
    private val textToSpeech: TextToSpeech,
) {
    /**
     * Empty sentence: no-op (non-blocking; no error) per US1 edge case.
     */
    suspend operator fun invoke(sentence: Sentence) {
        if (sentence.items.isEmpty()) return
        val spoken = buildString {
            for (id in sentence.items) {
                val p = vocabularyRepository.getPictogram(id) ?: continue
                if (isNotEmpty()) append(' ')
                append(p.spokenText)
            }
        }
        if (spoken.isBlank()) return
        textToSpeech.speak(spoken)
    }
}
