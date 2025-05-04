package com.droidnova.mangavision.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.droidnova.mangavision.data.local.entities.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga")
    fun getAllManga(): Flow<List<MangaEntity>>

    @Query("SELECT * FROM manga")
    fun getPagedManga(): PagingSource<Int, MangaEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangaList: List<MangaEntity>)

    @Query("DELETE FROM manga")
    suspend fun clearAll()
}
