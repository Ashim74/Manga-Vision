package com.droidnova.mangavision.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumb: String,
    val summary: String,
    val status: String,
    val totalChapter: Int
)
