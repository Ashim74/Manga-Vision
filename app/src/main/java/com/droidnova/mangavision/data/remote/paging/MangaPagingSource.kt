package com.droidnova.mangavision.data.remote.paging

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.droidnova.mangavision.data.local.dao.MangaDao
import com.droidnova.mangavision.data.mapper.toDomain
import com.droidnova.mangavision.data.mapper.toEntity
import com.droidnova.mangavision.data.remote.api.MangaApi
import com.droidnova.mangavision.domain.data.Manga
import com.droidnova.mangavision.utils.preloadImageToCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MangaPagingSource(
    private val context: Context,
    private val api: MangaApi,
    private val dao: MangaDao
) : PagingSource<Int, Manga>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Manga> {
        return try {
            val page = params.key ?: 1
            val response = api.fetchManga(page)
            val manga = response.data.map { it.toDomain() }

            if (page == 1 ) {
                val mangaEntities = response.data.map { it.toEntity() }
                withContext(Dispatchers.IO) {
                    dao.clearAll()
                    dao.insertAll(mangaEntities)
                }
            }

            LoadResult.Page(
                data = manga,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (manga.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Manga>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }


}
