package com.pictovoice.domain.model

data class GridLayout(
    val id: String,
    val rows: Int,
    val columns: Int,
    val version: Long,
    val cells: List<PictogramRef?>,
)

data class PictogramRef(val id: String)
