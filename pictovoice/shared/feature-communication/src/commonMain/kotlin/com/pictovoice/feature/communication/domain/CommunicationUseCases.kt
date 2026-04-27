package com.pictovoice.feature.communication.domain

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence

fun addPictogram(sentence: Sentence, pictogram: Pictogram): Sentence =
    sentence.copy(items = sentence.items + pictogram)

fun clearSentence(): Sentence = Sentence(emptyList())
