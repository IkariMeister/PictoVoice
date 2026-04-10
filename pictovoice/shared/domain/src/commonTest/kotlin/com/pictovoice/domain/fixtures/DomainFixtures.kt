package com.pictovoice.domain.fixtures

import com.pictovoice.domain.model.Pictogram
import com.pictovoice.domain.model.Sentence

object DomainFixtures {
    val samplePictograms: List<Pictogram> =
        listOf(
            Pictogram("a", "A", "Letter A", null, null, 0),
            Pictogram("b", "B", "Letter B", null, null, 1),
        )

    fun sentence(vararg ids: String): Sentence = Sentence(ids.toList())
}
