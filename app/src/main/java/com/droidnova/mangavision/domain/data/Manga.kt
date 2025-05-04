package com.droidnova.mangavision.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Manga(
    val id: String,
    val title: String,
    val thumb: String,
    val summary: String,
    val status: String,
    val totalChapter: Int
): Parcelable
