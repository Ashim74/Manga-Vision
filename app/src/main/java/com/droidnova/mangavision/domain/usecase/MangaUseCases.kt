package com.droidnova.mangavision.domain.usecase

import androidx.paging.PagingData
import com.droidnova.mangavision.data.repository.MangaRepository
import com.droidnova.mangavision.domain.data.Manga
import kotlinx.coroutines.flow.Flow

data class MangaUseCases(
    val getPagedManga: GetPagedMangaUseCase
)

class GetPagedMangaUseCase(private val repository: MangaRepository) {
    operator fun invoke(): Flow<PagingData<Manga>> = repository.getPagedManga()
}

