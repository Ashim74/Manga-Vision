package com.droidnova.mangavision.data.repository

import androidx.paging.PagingData
import com.droidnova.mangavision.domain.data.Manga
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getPagedManga(): Flow<PagingData<Manga>>
}
