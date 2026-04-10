package com.pictovoice.data.local

import app.cash.sqldelight.db.SqlDriver
import com.pictovoice.data.Pictogram as DbPictogram
import com.pictovoice.db.PictoVoiceDb
import com.pictovoice.domain.model.Pictogram

class LocalVocabularyDataSource(driver: SqlDriver) {
    private val db = PictoVoiceDb(driver)
    private val queries = db.vocabularyQueries

    fun insertPictogram(p: Pictogram) {
        queries.insertPictogram(
            id = p.id,
            label = p.label,
            spokenText = p.spokenText,
            imageRef = p.imageRef,
            category = p.category,
            sortOrder = p.sortOrder.toLong(),
        )
    }

    fun getPictogram(id: String): Pictogram? =
        queries.selectPictogramById(id).executeAsOneOrNull()?.toDomain()

    fun listAllPictograms(): List<Pictogram> =
        queries.selectAllPictograms().executeAsList().map { it.toDomain() }

    fun getMeta(key: String): String? =
        queries.selectMeta(key).executeAsOneOrNull()?.value_

    fun putMeta(key: String, value: String) {
        queries.insertMeta(key, value)
    }
}

private fun DbPictogram.toDomain(): Pictogram =
    Pictogram(
        id = id,
        label = label,
        spokenText = spokenText,
        imageRef = imageRef,
        category = category,
        sortOrder = sortOrder.toInt(),
    )
