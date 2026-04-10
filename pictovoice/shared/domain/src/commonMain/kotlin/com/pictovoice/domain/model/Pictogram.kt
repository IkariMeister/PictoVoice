package com.pictovoice.domain.model

data class Pictogram(
    val id: String,
    val label: String,
    val spokenText: String,
    val imageRef: String?,
    val category: String?,
    val sortOrder: Int,
)
