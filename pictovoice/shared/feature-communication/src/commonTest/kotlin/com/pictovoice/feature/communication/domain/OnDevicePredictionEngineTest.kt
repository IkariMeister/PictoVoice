package com.pictovoice.feature.communication.domain

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence
import kotlin.test.Test
import kotlin.test.assertEquals

class OnDevicePredictionEngineTest {
    @Test
    fun recent_sentence_items_are_ranked_first_without_duplicates() {
        val engine = OnDevicePredictionEngine()
        val predictions =
            engine(
                sentence =
                    Sentence(
                        listOf(
                            Pictogram("yes", "Yes", "Yes"),
                            Pictogram("water", "Water", "Water"),
                            Pictogram("yes", "Yes", "Yes"),
                        ),
                    ),
                vocabulary =
                    listOf(
                        Pictogram("yes", "Yes", "Yes"),
                        Pictogram("no", "No", "No"),
                        Pictogram("water", "Water", "Water"),
                        Pictogram("help", "Help", "Help"),
                    ),
            )

        assertEquals(listOf("yes", "water", "no", "help"), predictions.map { it.id })
    }
}
