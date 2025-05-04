package com.droidnova.mangavision.data.repositoryImpl

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.droidnova.mangavision.data.local.dao.MangaDao
import com.droidnova.mangavision.data.mapper.toDomain
import com.droidnova.mangavision.data.remote.api.MangaApi
import com.droidnova.mangavision.data.remote.paging.MangaPagingSource
import com.droidnova.mangavision.data.repository.MangaRepository
import com.droidnova.mangavision.domain.data.Manga
import com.droidnova.mangavision.utils.isConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val api: MangaApi,
    private val dao: MangaDao,
    private val context: Context
) : MangaRepository {

    override fun getPagedManga(): Flow<PagingData<Manga>> {
        return if (isConnected(context)) {
            Log.e("myTag","fetching data online")
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { MangaPagingSource(context,api,dao) }
            ).flow
        } else {
            Log.e("myTag","fetching data offine")
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { dao.getPagedManga() }
            ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
        }
    }


}
