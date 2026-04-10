package com.pictovoice.domain.repo

import com.pictovoice.domain.model.Pictogram

/**
 * Local-first vocabulary access. Remote sync is optional in later phases.
 */
interface VocabularyRepository {
    suspend fun getPictogram(id: String): Pictogram?
    suspend fun listPictogramsForGrid(): List<Pictogram>
}
