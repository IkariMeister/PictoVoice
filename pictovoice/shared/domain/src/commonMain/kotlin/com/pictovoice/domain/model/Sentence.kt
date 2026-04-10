package com.pictovoice.domain.model

data class Sentence(
    val items: List<String>,
    val revision: Long = 0L,
)
