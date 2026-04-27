package com.pictovoice.feature.vocabulary.domain

import com.pictovoice.core.model.Pictogram

interface VocabularyRepository {
    suspend fun listPictograms(): List<Pictogram>
    suspend fun getPictogramById(id: String): Pictogram?
}
