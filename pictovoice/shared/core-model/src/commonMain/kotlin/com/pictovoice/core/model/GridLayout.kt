package com.pictovoice.core.model

data class GridLayout(
    val id: String,
    val rows: Int,
    val columns: Int,
    val cells: List<Pictogram?>,
    val version: Long,
)
