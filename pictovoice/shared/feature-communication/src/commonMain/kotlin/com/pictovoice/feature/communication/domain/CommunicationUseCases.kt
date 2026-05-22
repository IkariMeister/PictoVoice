package com.pictovoice.feature.communication.domain

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository

fun addPictogram(sentence: Sentence, pictogram: Pictogram): Sentence =
    sentence.copy(items = sentence.items + pictogram)

fun removePictogramAt(sentence: Sentence, index: Int): Sentence {
    if (index !in sentence.items.indices) return sentence
    val next = sentence.items.toMutableList().apply { removeAt(index) }
    return sentence.copy(items = next)
}

fun clearSentence(): Sentence = Sentence(emptyList())

suspend fun speakSentence(
    sentence: Sentence,
    vocabularyRepository: VocabularyRepository,
    textToSpeechEngine: TextToSpeechEngine,
) {
    if (sentence.items.isEmpty()) return
    val spoken =
        sentence.items
            .mapNotNull { vocabularyRepository.getPictogramById(it.id)?.spokenText }
            .joinToString(" ")
            .trim()
    if (spoken.isNotEmpty()) {
        textToSpeechEngine.speak(spoken)
    }
}
