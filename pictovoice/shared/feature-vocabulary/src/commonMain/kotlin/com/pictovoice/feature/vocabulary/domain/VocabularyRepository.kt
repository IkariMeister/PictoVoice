package com.pictovoice.feature.vocabulary.domain

import com.pictovoice.core.model.Pictogram

/**
 * Local-first vocabulary repository.
 *
 * Offline invariant:
 * - `listPictograms` and `getPictogramById` must work without network connectivity.
 * - Speech-critical flows must not block on remote calls.
 */
interface VocabularyRepository {
    suspend fun listPictograms(): List<Pictogram>

    suspend fun getPictogramById(id: String): Pictogram?
}
