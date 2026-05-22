package com.pictovoice.feature.communication.domain

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EditSentenceHandlersTest {
    @Test
    fun removePictogramAt_removes_item_whenIndexInBounds() {
        val sentence =
            Sentence(
                items =
                    listOf(
                        Pictogram("yes", "Yes", "Yes"),
                        Pictogram("no", "No", "No"),
                        Pictogram("water", "Water", "Water"),
                    ),
            )
        val result = removePictogramAt(sentence, 1)

        assertEquals(listOf("yes", "water"), result.items.map { it.id })
    }

    @Test
    fun removePictogramAt_returnsSameSentence_whenIndexOutOfBounds() {
        val sentence =
            Sentence(
                items =
                    listOf(
                        Pictogram("yes", "Yes", "Yes"),
                        Pictogram("water", "Water", "Water"),
                    ),
            )

        val negative = removePictogramAt(sentence, -1)
        val tooLarge = removePictogramAt(sentence, 5)

        assertEquals(sentence, negative)
        assertEquals(sentence, tooLarge)
    }

    @Test
    fun clearSentence_empties_items() {
        val result = clearSentence()
        assertTrue(result.items.isEmpty())
    }
}
