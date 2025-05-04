package com.droidnova.mangavision.data.remote.dto

data class MangaDto(
    val id: String,
    val title: String,
    val thumb: String,
    val summary: String,
    val status: String,
    val total_chapter: Int
)

