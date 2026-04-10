package com.pictovoice.data.repo

import com.pictovoice.data.local.LocalVocabularyDataSource
import com.pictovoice.domain.model.Pictogram
import com.pictovoice.domain.repo.VocabularyRepository

/**
 * Local-first repository. v1 uses only [LocalVocabularyDataSource] (no remote on Speak path).
 */
class DefaultVocabularyRepository(
    private val local: LocalVocabularyDataSource,
) : VocabularyRepository {
    override suspend fun getPictogram(id: String): Pictogram? = local.getPictogram(id)

    override suspend fun listPictogramsForGrid(): List<Pictogram> = local.listAllPictograms()
}
