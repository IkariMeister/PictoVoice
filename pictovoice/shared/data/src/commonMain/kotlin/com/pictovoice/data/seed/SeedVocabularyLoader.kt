package com.pictovoice.data.seed

import com.pictovoice.data.local.LocalVocabularyDataSource
import com.pictovoice.domain.model.Pictogram
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object SeedVocabularyLoader {
    private const val META_KEY = "seed_applied_v1"
    private val json = Json { ignoreUnknownKeys = true }

    fun loadIfNeeded(local: LocalVocabularyDataSource, jsonText: String) {
        if (local.getMeta(META_KEY) == "1") return
        val rows = json.decodeFromString<SeedFile>(jsonText).pictograms
        for (p in rows) {
            local.insertPictogram(
                Pictogram(
                    id = p.id,
                    label = p.label,
                    spokenText = p.spokenText,
                    imageRef = p.imageRef,
                    category = p.category,
                    sortOrder = p.sortOrder,
                ),
            )
        }
        local.putMeta(META_KEY, "1")
    }
}

@Serializable
private data class SeedFile(
    val pictograms: List<SeedPictogram>,
)

@Serializable
private data class SeedPictogram(
    val id: String,
    val label: String,
    @SerialName("spoken_text") val spokenText: String,
    @SerialName("image_ref") val imageRef: String? = null,
    val category: String? = null,
    @SerialName("sort_order") val sortOrder: Int,
)
